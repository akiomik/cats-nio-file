# cats-nio-file

[![Download](https://api.bintray.com/packages/akiomik/maven/cats-nio-file/images/download.svg)](https://bintray.com/akiomik/maven/cats-nio-file/_latestVersion)
[![Build Status](https://travis-ci.org/akiomik/cats-nio-file.svg?branch=master)](https://travis-ci.org/akiomik/cats-nio-file)

A thin scala wrapper for Java NIO.2, built on cats-effect 🐱

## Getting started

cats-nio-file is currently available for Scala 2.12 and 2.13.

Add the following lines to your `build.sbt`.

```scala
resolvers += Resolver.bintrayRepo("akiomik", "maven")

libraryDependencies += "com.github.akiomik" %% "cats-nio-file" % "1.0.0"
```

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
