# cats-nio-file

[![Latest version](https://index.scala-lang.org/akiomik/cats-nio-file/cats-nio-file/latest.svg?color=blue&style=flat)](https://index.scala-lang.org/akiomik/cats-nio-file/cats-nio-file)
[![Scala CI](https://github.com/akiomik/cats-nio-file/workflows/Scala%20CI/badge.svg)](https://github.com/akiomik/cats-nio-file/actions?query=workflow%3A%22Scala+CI%22)

A thin scala wrapper for Java NIO.2, built on cats-effect 🐱

## Getting started

cats-nio-file is currently available for Scala 2.12, 2.13 and 3.0.

Add the following lines to your `build.sbt`.

```scala
libraryDependencies += "io.github.akiomik" %% "cats-nio-file" % "1.6.0"
```

NOTE: The groupid has been changed from `com.github.akiomik` to `io.github.akiomik` because the maven repository has been changed from bintray to sonatype.

## All releases

cats-nio-file supports some different versions of cats-effect.

| cats-nio-file version | cats-effect version | scala version       |
| --------------------- | ------------------- | ------------------- |
| 1.0.1                 | 2.0.x               | 2.12.x/2.13.x       |
| 1.1.0                 | 2.1.x               | 2.12.x/2.13.x       |
| 1.2.0                 | 2.2.x               | 2.12.x/2.13.x       |
| 1.3.0                 | 2.3.x               | 2.12.x/2.13.x       |
| 1.4.0                 | 3.0.x               | 2.12.x/2.13.x       |
| 1.5.0                 | 3.1.x               | 2.12.x/2.13.x       |
| 1.6.0                 | 3.2.x               | 2.12.x/2.13.x/3.0.x |

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

// Java collections are converted to Scala collections through `scala.jdk.CollectionConverters`
val perms: IO[mutable.Set[PosixFilePermission]] = Files[IO].getPosixFilePermissions(Paths.get("build.sbt"))

// `IO[A <: AutoCloseable]` values are able to convert to `Resource[IO, A]` (e.g. `JStream[A]`, `BufferedReader`, `BufferedWriter`, `SeekableByteChannel`, and `DirectoryStream[A]`)
import cats.nio.file.implicits._
val list: Resource[IO, JStream[Path]] = Files[IO].list(Paths.get(".")).resource
```
