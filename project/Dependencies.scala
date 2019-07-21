import sbt._

object Dependencies {
  lazy val scalaJava8Compat = "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.0"
  lazy val catsEffect       = "org.typelevel"          %% "cats-effect"        % "2.0.0-M4"
  lazy val scalaTest        = "org.scalatest"          %% "scalatest"          % "3.0.8"
}
