import Dependencies._

lazy val v2_12 = "2.12.8"
lazy val v2_13 = "2.13.0"

ThisBuild / crossScalaVersions := Seq(v2_13, v2_12)
ThisBuild / scalaVersion       := crossScalaVersions.value.head
ThisBuild / version            := "1.0.0-SNAPSHOT"
ThisBuild / organization       := "com.github.akiomik"

lazy val root = (project in file("."))
  .settings(
    name := "cats-nio-file",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Ywarn-unused",
      "-Ywarn-value-discard",
    ),
    libraryDependencies ++= Seq(
      scalaJava8Compat,
      catsEffect,
      scalaTest % Test,
    )
  )
