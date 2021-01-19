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

import cats.nio.file.implicits._
import cats.nio.file.compat.CollectionConverter._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class JavaStreamOpsTests extends AnyFunSuite with Matchers {
  test("resource") {
    val expected = Vector(
      Paths.get("src/test/scala/cats/nio/file/JavaStreamOpsTests.scala"),
      Paths.get("src/test/scala/cats/nio/file/FilesTests.scala"),
      Paths.get("src/main/scala/cats/nio/file/implicits/package.scala"),
      Paths.get("src/main/scala/cats/nio/file/Files.scala"),
      Paths.get("src/main/scala-2.12/cats/nio/file/compat/package.scala"),
      Paths.get("src/main/scala-2.13/cats/nio/file/compat/package.scala")
    )

    var closed = false

    Files[IO]
      .find(Paths.get("src"), 100, (path, _) => path.toString.endsWith(".scala"))
      .map(_.onClose(() => closed = true))
      .resource
      .use { stream =>
        IO.pure {
          stream.iterator.asScala.toVector should contain theSameElementsAs expected
        }
      }
      .unsafeRunSync()

    closed should be(true)
  }
}
