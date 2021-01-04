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

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import org.scalatest.{FunSuite, Matchers}

import cats.nio.file.compat.CollectionConverter._

class FilesTests extends FunSuite with Matchers {
  test("scenario") {
    val content1 =
      """One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin.
        |He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections.
        |""".stripMargin

    (for {
      tempDir  <- Files[IO].createTempDirectory("cats-nio-file")
      file1 = tempDir.resolve(Paths.get("file1.txt"))
      _        <- Files[IO].createFile(file1)
      _        <- Files[IO].write(file1, content1.linesIterator.toSeq)
      file2 = tempDir.resolve(Paths.get("file2.txt"))
      _        <- Files[IO].copy(file1, file2)
      content2 <- Files[IO].readAllLines(file2)
      _        <- Files[IO].delete(file1)
      exists1  <- Files[IO].exists(file1)
      file3 = tempDir.resolve(Paths.get("file3.txt"))
      _        <- Files[IO].move(file2, file3)
      size     <- Files[IO].size(file3)
      exists3  <- Files[IO].deleteIfExists(file3)
    } yield {
      content2 should === (content1.linesIterator.toSeq)
      exists1  should === (false)
      size     should === (content1.size)
      exists3  should === (true)
    }).unsafeRunSync()
  }

  test("find") {
    val files =
      Files[IO]
        .find(Paths.get("src"), 100, (path, _) => path.toString.endsWith(".scala"))
        .unsafeRunSync()
    val expected = Vector(
      Paths.get("src/test/scala/cats/nio/file/JavaStreamOpsTests.scala"),
      Paths.get("src/test/scala/cats/nio/file/FilesTests.scala"),
      Paths.get("src/main/scala/cats/nio/file/implicits/package.scala"),
      Paths.get("src/main/scala/cats/nio/file/Files.scala"),
      Paths.get("src/main/scala-2.12/cats/nio/file/compat/package.scala"),
      Paths.get("src/main/scala-2.13/cats/nio/file/compat/package.scala"),
    )

    try {
      files.iterator.asScala.toVector should contain theSameElementsAs (expected)
    } finally {
      files.close
    }
  }
}
