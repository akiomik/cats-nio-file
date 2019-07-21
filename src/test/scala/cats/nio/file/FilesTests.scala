package cats.nio.file

import java.nio.file.Paths

import scala.collection.JavaConverters._ // TODO: use scala.jdk.CollectionConverters._
import scala.concurrent.ExecutionContext

import cats.effect.{ContextShift, IO}
import org.scalatest.{FunSuite, Matchers}

class FilesTests extends FunSuite with Matchers {
  implicit val ec: ExecutionContext = ExecutionContext.global
  implicit val cs: ContextShift[IO] = IO.contextShift(ec)

  test("scenario") {
    val content1 =
      """One morning, when Gregor Samsa woke from troubled dreams, he found himself transformed in his bed into a horrible vermin.
        |He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections.
        |""".stripMargin

    (for {
      tempDir  <- Files[IO].createTempDirectory("cats-nio-file")
      file1 = tempDir.resolve(Paths.get("file1.txt"))
      _        <- Files[IO].createFile(file1)
      _        <- Files[IO].write(file1, content1.lines.toIterable)
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
      content2 should === (content1.lines.toIterable)
      exists1  should === (false)
      size     should === (content1.size)
      exists3  should === (true)
    }).unsafeRunSync
  }

  test("find") {
    val files =
      Files[IO]
        .find(Paths.get("src"), 100, (path, _) => path.toString.endsWith(".scala"))
        .unsafeRunSync
    val expected = Vector(
      Paths.get("src/test/scala/cats/nio/file/JavaStreamOpsTests.scala"),
      Paths.get("src/test/scala/cats/nio/file/FilesTests.scala"),
      Paths.get("src/main/scala/cats/nio/file/implicits/package.scala"),
      Paths.get("src/main/scala/cats/nio/file/Files.scala"),
    )

    try {
      files.iterator.asScala.toVector should === (expected)
    } finally {
      files.close
    }
  }
}
