import sbt._
import Keys._

object IngredientsBuild extends Build {
  lazy val commonSettings = seq(
    organization  := "io.buildo",
    scalaVersion  := "2.11.5",
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

  lazy val jsend = project
    .settings(commonSettings: _*)

  lazy val logging = project
    .settings(commonSettings: _*)

  lazy val tuplecutter = project
    .settings(commonSettings: _*)

  lazy val root = project.in(file("."))
    .aggregate(jsend, logging, tuplecutter)
    .settings(
      publish := {}
    )

}
