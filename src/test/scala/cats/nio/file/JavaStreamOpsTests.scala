package cats.nio.file

import java.nio.file.Paths

import scala.collection.JavaConverters._ // TODO: use scala.jdk.CollectionConverters._
import scala.concurrent.ExecutionContext

import cats.effect.{ContextShift, IO}
import org.scalatest.{FunSuite, Matchers}

import cats.nio.file.implicits._

class JavaStreamOpsTests extends FunSuite with Matchers {
  implicit val ec: ExecutionContext = ExecutionContext.global
  implicit val cs: ContextShift[IO] = IO.contextShift(ec)

  test("resource") {
    var closed = false

    Files[IO]
      .find(Paths.get("src"), 100, (path, _) => path.toString.endsWith(".scala"))
      .map(_.onClose(new Runnable {
        def run = {
          closed = true
        }
      })).resource.use { stream =>
        val expected = Vector(
          Paths.get("src/test/scala/cats/nio/file/JavaStreamOpsTests.scala"),
          Paths.get("src/test/scala/cats/nio/file/FilesTests.scala"),
          Paths.get("src/main/scala/cats/nio/file/implicits/package.scala"),
          Paths.get("src/main/scala/cats/nio/file/Files.scala"),
        )

        IO.pure {
          stream.iterator.asScala.toVector should === (expected)
        }
      }.unsafeRunSync

    closed should === (true)
  }
}

