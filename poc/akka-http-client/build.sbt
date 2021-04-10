import sbt.Keys.libraryDependencies

lazy val scalaLoggingVersion = "3.9.3"
lazy val loggingVersion = "2.14.1"
lazy val akkaVersion = "2.6.13"
lazy val akkaHttpVersion = "10.2.4"
lazy val json4sVersion = "3.6.11"
lazy val scalaXmlVersion = "1.3.0"
lazy val projectVersion = "1.0.2"
lazy val projectName = "akka-http-client"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := projectVersion,
  javacOptions ++= Seq("-source", "11"),
  scalaVersion := "2.13.5",
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "org.json4s" %% "json4s-native" % json4sVersion,
    "org.json4s" %% "json4s-jackson" % json4sVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
    "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
    "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion
  )
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := projectName
  )