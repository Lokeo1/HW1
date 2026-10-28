val scalaVer = "2.12.21"

ThisBuild / scalaVersion := scalaVer

val sjcInputKey = {
  val sjc = inputKey[Unit]("Run sjc")
  sjc
}

lazy val commonSettings = Seq(
  organization := "CSE411",
  scalaVersion := scalaVer,
  scalacOptions ++= Seq(
    "-target:jvm-1.8",
    "-deprecation",
    "-Ydelambdafy:method",
    "-feature",
    "-unchecked"
  ),
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
  javacOptions in (Compile, doc) := Seq("-notimestamp", "-linksource"),
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.1",
    "org.antlr" % "antlr4-runtime" % "4.13.1",
    "org.antlr" % "ST4" % "4.3.1",
    "org.antlr" % "antlr4" % "4.13.1",
    "org.ow2.asm" % "asm" % "8.0.1",
    "org.ow2.asm" % "asm-commons" % "8.0.1",
    "org.ow2.asm" % "asm-util" % "8.0.1",
    "org.eclipse.platform" % "org.eclipse.core.resources" % "3.13.700",
    "org.eclipse.platform" % "org.eclipse.core.runtime" % "3.18.0",
    "org.eclipse.platform" % "org.eclipse.equinox.app" % "1.4.500",
    "org.eclipse.jdt" % "org.eclipse.jdt.core" % "3.22.0",
    "com.novocode" % "junit-interface" % "0.11" % "test"
  ),
  testOptions += Tests.Argument(TestFrameworks.JUnit, "-v"),
  autoAPIMappings := true,
  crossPaths := false
)

lazy val sjc = (project in file("sjc"))
  .settings(commonSettings: _*)
  .settings(
    fullRunInputTask(sjcInputKey, Compile, "sjc.SJC")
  )

lazy val examples =
  (project in file("examples")).settings(commonSettings: _*).dependsOn(sjc)

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

lazy val topDownParser =
  (project in file("top-down-parser"))
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies += "junit" % "junit" % "4.13.2" % Test
    )
    .dependsOn(simpleLexerSubset)

lazy val bottomUpParser =
  (project in file("bottom-up-parser"))
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies += "junit" % "junit" % "4.13.2" % Test
    )
    .dependsOn(topDownParser, simpleLexerSubset)

lazy val sjcParser =
  (project in file("sjc-parser"))
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies += "junit" % "junit" % "4.13.2" % Test
    )

lazy val sjcAst =
  (project in file("sjc-ast"))
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies += "junit" % "junit" % "4.13.2" % Test
    )
    .dependsOn(sjcParser)
