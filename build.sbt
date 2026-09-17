val scalaVer = "2.12.21"

ThisBuild / scalaVersion := scalaVer

lazy val commonSettings = Seq(
  organization := "CSE411",
  scalaVersion := scalaVer,
  scalacOptions ++= Seq(
    "-target:jvm-26",
    "-deprecation",
    "-Ydelambdafy:method",
    "-feature",
    "-unchecked"
  ),
  javacOptions ++= Seq("-source", "26", "-target", "26"),
  Compile / doc / javacOptions := Seq("-notimestamp", "-linksource"),
  libraryDependencies ++= Seq(
    "com.novocode" % "junit-interface" % "0.11" % "test"
  ),
  testOptions += Tests.Argument(TestFrameworks.JUnit, "-v"),
  autoAPIMappings := true,
  crossPaths := false
)

lazy val simpleLexer =
  (project in file("simple-lexer"))
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies += "junit" % "junit" % "4.13.2" % Test
    )

lazy val simpleLexerSubset =
  (project in file("simple-lexer-subset"))
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies += "junit" % "junit" % "4.13.2" % Test
    )
