import sbt._
import Keys._
import bintray.BintrayKeys._

object IngredientsBuild extends Build {
  lazy val commonSettings = seq(
    organization  := "io.buildo",
    scalaVersion  := "2.11.8",
    crossScalaVersions := Seq("2.11.8", "2.12.1"),
    resolvers ++= Seq(
      "buildo mvn" at "https://raw.github.com/buildo/mvn/master/releases",
      Resolver.jcenterRepo
    ),
    scalacOptions := Seq(
      "-unchecked",
      "-deprecation",
      "-encoding",
      "utf8"),
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    bintrayOrganization := Some("buildo"),
    bintrayReleaseOnPublish in ThisBuild := true
  )

  lazy val jsend = project.in(file("jsend"))
    .settings(commonSettings: _*)

  lazy val logging = project.in(file("logging"))
    .settings(commonSettings: _*)
    .dependsOn(loggingMacro % "compile-internal, test-internal")

  lazy val loggingMacro = project.in(file("logging/macro"))
    .settings(commonSettings: _*)
    .settings(
      publish := (),
      publishLocal := ()
    )

  lazy val tuplecutter = project.in(file("tuplecutter"))
    .settings(commonSettings: _*)

  lazy val caseenum = project.in(file("caseenum"))
    .settings(commonSettings: _*)

  lazy val caseenumCirceSupport = project.in(file("caseenum/circeSupport"))
    .settings(commonSettings: _*)
    .dependsOn(caseenum)

  lazy val root = project.in(file("."))
    .aggregate(jsend, logging, tuplecutter, caseenum)
    .settings(
      publish := {}
    )

}
