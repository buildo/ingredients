organization  := "io.buildo"

name := "ingredients-jsend"

version       := "0.2-SNAPSHOT"

scalaVersion  := "2.11.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
 
resolvers ++= Seq(
  "buildo mvn" at "https://raw.github.com/buildo/mvn/master/releases"
)

libraryDependencies ++= Seq(
  "org.scala-lang" %  "scala-reflect" % "2.11.2",
  "io.buildo"      %% "spray-autoproductformat" % "0.1-SNAPSHOT",
  "org.scalatest"  %% "scalatest"     % "2.2.0" % "test",
  "org.mockito"    %  "mockito-all"   % "1.9.5" % "test"
)

publishTo := Some(Resolver.file("file", new File("releases")))
