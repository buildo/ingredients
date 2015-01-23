import sbt._
import Keys._

object IngredientsJSendBuild extends Build {
  lazy val root = Project(id = "ingredients-logging", base = file("."))
}
