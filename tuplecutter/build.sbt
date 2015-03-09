organization  := "io.buildo"

name := "ingredients-tuplecutter"

version       := "0.1.1"

scalaVersion  := "2.11.5"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= Seq(
  "org.scalatest"  %% "scalatest"     % "2.2.0" % "test"
)

Boilerplate.settings

publishTo := Some(Resolver.file("file", new File("releases")))