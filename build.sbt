import Dependencies._

lazy val v2_12 = "2.12.12"
lazy val v2_13 = "2.13.4"

crossScalaVersions := Seq(v2_13, v2_12)
scalaVersion := crossScalaVersions.value.head
version := "1.4.0"
organization := "io.github.akiomik"
homepage := Some(url("https://github.com/akiomik/cats-nio-file"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/akiomik/cats-nio-file"),
    "git@github.com:akiomik/cats-nio-file.git"
  )
)
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
developers := List(
  Developer(
    id = "akiomik",
    name = "Akiomi Kamakura",
    email = "akiomik@gmail.com",
    url = url("https://github.com/akiomik")
  )
)
semanticdbEnabled := true
semanticdbVersion := scalafixSemanticdb.revision

lazy val root = (project in file("."))
  .settings(
    name := "cats-nio-file",
    description := "A thin scala wrapper for Java NIO.2, built on cats-effect",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-language:higherKinds",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused",
      "-Ywarn-value-discard"
    ),
    libraryDependencies ++= Seq(
      scalaJava8Compat,
      catsEffect,
      scalaTest                  % Test,
      catsEffectTestingScalaTest % Test
    )
  )

sonatypeCredentialHost := "s01.oss.sonatype.org"
publishTo := sonatypePublishToBundle.value
publishMavenStyle := true
