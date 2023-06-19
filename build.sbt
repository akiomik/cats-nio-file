import Dependencies._

lazy val v2_12 = "2.12.14"
lazy val v2_13 = "2.13.6"
lazy val v3_2  = "3.2.2"

crossScalaVersions := Seq(v3_2, v2_13, v2_12)
scalaVersion       := crossScalaVersions.value.head
version            := "1.8.0"
organization       := "io.github.akiomik"
homepage           := Some(url("https://github.com/akiomik/cats-nio-file"))
scmInfo            := Some(
  ScmInfo(
    url("https://github.com/akiomik/cats-nio-file"),
    "git@github.com:akiomik/cats-nio-file.git"
  )
)
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
developers         := List(
  Developer(
    id = "akiomik",
    name = "Akiomi Kamakura",
    email = "akiomik@gmail.com",
    url = url("https://github.com/akiomik")
  )
)
semanticdbEnabled  := true
semanticdbVersion  := scalafixSemanticdb.revision

lazy val scala2scalacOptions =
  Seq("-Xlint", "-Ywarn-dead-code", "-Ywarn-numeric-widen", "-Ywarn-value-discard")
lazy val scala3scalacOptions = Seq.empty

lazy val root = (project in file("."))
  .settings(
    name        := "cats-nio-file",
    description := "A thin scala wrapper for Java NIO.2, built on cats-effect",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-language:higherKinds",
      "-Ywarn-unused" // This option is ignored in scala 3 but need for scalafix
    ) ++ (if (scalaVersion.value.startsWith("3.0")) scala3scalacOptions else scala2scalacOptions),
    libraryDependencies ++= Seq(
      scalaJava8Compat,
      catsEffect,
      scalaTest                  % Test,
      catsEffectTestingScalaTest % Test
    )
  )

sonatypeCredentialHost := "s01.oss.sonatype.org"
publishTo              := sonatypePublishToBundle.value
publishMavenStyle      := true
