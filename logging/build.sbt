name := "ingredients-logging"

version       := "0.5.2"

libraryDependencies <+= (scalaVersion) { sv =>
  "org.scala-lang" %  "scala-reflect" % sv
}

libraryDependencies ++= Seq(
  "org.scalatest"  %% "scalatest"     % "2.2.0" % "test",
  "org.mockito"    %  "mockito-all"   % "1.9.5" % "test"
)
