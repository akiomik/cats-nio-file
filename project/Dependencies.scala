import sbt._

object Dependencies {
  lazy val scalaJava8Compat           = "org.scala-lang.modules" %% "scala-java8-compat"            % "1.0.0"
  lazy val catsEffect                 = "org.typelevel"          %% "cats-effect"                   % "3.2.1"
  lazy val scalaTest                  = "org.scalatest"          %% "scalatest"                     % "3.2.9"
  lazy val catsEffectTestingScalaTest = "org.typelevel"          %% "cats-effect-testing-scalatest" % "1.2.0"
}
