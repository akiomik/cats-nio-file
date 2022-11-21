import sbt._

object Dependencies {
  lazy val scalaJava8Compat           = "org.scala-lang.modules" %% "scala-java8-compat"            % "1.0.2"
  lazy val catsEffect                 = "org.typelevel"          %% "cats-effect"                   % "3.3.14"
  lazy val scalaTest                  = "org.scalatest"          %% "scalatest"                     % "3.2.14"
  lazy val catsEffectTestingScalaTest = "org.typelevel"          %% "cats-effect-testing-scalatest" % "1.5.0"
}
