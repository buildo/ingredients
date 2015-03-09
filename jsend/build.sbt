name := "ingredients-jsend"

version       := "0.2-SNAPSHOT"

libraryDependencies ++= Seq(
  "io.buildo"      %% "spray-autoproductformat" % "0.1-SNAPSHOT",
  "org.scalatest"  %% "scalatest"     % "2.2.0" % "test",
  "org.mockito"    %  "mockito-all"   % "1.9.5" % "test"
)

publishTo := Some(Resolver.file("file", new File("releases")))
