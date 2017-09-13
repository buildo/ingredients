name := "ingredients-jsend"

version       := "0.2.10"
scalaVersion  := "2.11.11"

libraryDependencies ++= Seq(
  "io.spray"       %% "spray-json" % "1.3.2",
  "org.scalatest"  %% "scalatest"     % "2.2.0" % "test",
  "org.mockito"    %  "mockito-all"   % "1.9.5" % "test"
)
