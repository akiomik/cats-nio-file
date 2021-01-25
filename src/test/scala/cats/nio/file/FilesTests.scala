// Copyright 2019 Akiomi Kamakura
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package cats.nio.file

import java.nio.file.Paths

import cats.effect.{IO, Resource}
import cats.effect.unsafe.implicits.global

import cats.nio.file.compat.CollectionConverter._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class FilesTests extends AnyFunSuite with Matchers {
  test("scenario") {
    val content1 =
      """One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin.
        |He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections.
        |""".stripMargin

    (for {
      tempDir  <- Files[IO].createTempDirectory("cats-nio-file")
      file1     = tempDir.resolve(Paths.get("file1.txt"))
      _        <- Files[IO].createFile(file1)
      _        <- Files[IO].write(file1, content1.linesIterator.toSeq)
      file2     = tempDir.resolve(Paths.get("file2.txt"))
      _        <- Files[IO].copy(file1, file2)
      content2 <- Files[IO].readAllLines(file2)
      _        <- Files[IO].delete(file1)
      exists1  <- Files[IO].exists(file1)
      file3     = tempDir.resolve(Paths.get("file3.txt"))
      _        <- Files[IO].move(file2, file3)
      size     <- Files[IO].size(file3)
      exists3  <- Files[IO].deleteIfExists(file3)
    } yield {
      content2 should be(content1.linesIterator.toSeq)
      exists1 should be(false)
      size should be(content1.size)
      exists3 should be(true)
    }).unsafeRunSync()
  }

  test("find") {
    val expected = Vector(
      Paths.get("src/test/scala/cats/nio/file/JavaStreamOpsTests.scala"),
      Paths.get("src/test/scala/cats/nio/file/FilesTests.scala"),
      Paths.get("src/main/scala/cats/nio/file/implicits/package.scala"),
      Paths.get("src/main/scala/cats/nio/file/Files.scala"),
      Paths.get("src/main/scala-2.12/cats/nio/file/compat/package.scala"),
      Paths.get("src/main/scala-2.13/cats/nio/file/compat/package.scala")
    )

    Resource
      .fromAutoCloseable {
        Files[IO].find(Paths.get("src"), 100, (path, _) => path.toString.endsWith(".scala"))
      }
      .use { stream =>
        IO.pure {
          stream.iterator.asScala.toVector should contain theSameElementsAs expected
        }
      }
      .unsafeRunSync()
  }
}
