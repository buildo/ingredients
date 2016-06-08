name := "ingredients-caseenum"

version       := "0.2.1"

libraryDependencies <+= (scalaVersion) { sv =>
  "org.scala-lang" %  "scala-reflect" % sv
}

libraryDependencies ++= Seq(
  "org.scalatest"  %% "scalatest"     % "2.2.0" % "test",
  "org.mockito"    %  "mockito-all"   % "1.9.5" % "test"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0-M5" cross CrossVersion.full)
