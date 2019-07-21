# cats-nio-file

A thin scala wrapper for Java NIO.2, built on cats-effect 🐱

## Usage

```scala
import java.util.stream.{Stream => JStream}
import java.nio.file.{Path, Paths}
import java.nio.file.attribute.PosixFilePermission
import scala.collection.mutable

import cats.effect.{IO, Resource}
import cats.nio.file.Files

// File I/O operations are wrapped with `F[_]` (e.g. `IO`)
val exists: IO[Boolean] = Files[IO].exists(Paths.get("build.sbt"))

// Java collections are converted to Scala collections through `scala.collection.JavaConverters`
val perms: IO[mutable.Set[PosixFilePermission]] = Files[IO].getPosixFilePermissions(Paths.get("build.sbt"))

// `IO[JStream[A]]` values are able to convert to `Resource`
import cats.nio.file.implicits._
val list: Resource[IO, JStream[Path]] = Files[IO].list(Paths.get(".")).resource
```
