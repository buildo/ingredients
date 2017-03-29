name := "ingredients-caseenum"

version := "0.3.0"

libraryDependencies <+= (scalaVersion) { sv =>
  "org.scala-lang" %  "scala-reflect" % sv
}

libraryDependencies ++= Seq(
  "org.scalatest"  %% "scalatest"     % "3.0.1" % "test",
  "org.mockito"    %  "mockito-all"   % "1.9.5" % "test"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
