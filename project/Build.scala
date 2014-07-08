import sbt._
import Keys._

object IngredientsLoggingBuild extends Build {
  lazy val root = Project(id = "ingredients-logging", base = file("."))
}
