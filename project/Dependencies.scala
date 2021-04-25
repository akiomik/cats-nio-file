import sbt._

object Dependencies {
  lazy val scalaJava8Compat = "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.1"
  lazy val catsEffect       = "org.typelevel"          %% "cats-effect"        % "3.0.2"
  lazy val scalaTest        = "org.scalatest"          %% "scalatest"          % "3.2.7"
}
