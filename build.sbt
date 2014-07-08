organization  := "io.buildo"

name := "ingredients-logging"

version       := "0.1-SNAPSHOT"

scalaVersion  := "2.11.0"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.11.0"
)

publishTo := Some(Resolver.file("file", new File("releases")))
