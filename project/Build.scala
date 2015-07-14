import sbt._
import Keys._

object IngredientsBuild extends Build {
  lazy val commonSettings = seq(
    organization  := "io.buildo",
    scalaVersion  := "2.11.6",
    resolvers ++= Seq(
      "buildo mvn" at "https://raw.github.com/buildo/mvn/master/releases"
    ),
    scalacOptions := Seq(
      "-unchecked",
      "-deprecation",
      "-encoding",
      "utf8"),
    publishTo := Some(Resolver.file("file", new File("releases")))
  )

  lazy val jsend = project.in(file("jsend"))
    .settings(commonSettings: _*)

  lazy val logging = project.in(file("logging"))
    .settings(commonSettings: _*)
    .dependsOn(loggingMacro)

  lazy val loggingMacro = project.in(file("logging/macro"))
    .settings(commonSettings: _*)

  lazy val tuplecutter = project.in(file("tuplecutter"))
    .settings(commonSettings: _*)

  lazy val root = project.in(file("."))
    .aggregate(jsend, logging, tuplecutter)
    .settings(
      publish := {}
    )

}
