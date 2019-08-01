import sbt.Keys.libraryDependencies

lazy val akkaVersion = "2.5.23"
lazy val scalatestVersion = "3.0.8"
lazy val scalaCheckVersion = "1.14.0"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.3",
  scalaVersion := "2.13.0",
  scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "org.scalatest" %% "scalatest" % scalatestVersion % "test",
    "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"
  )
)

lazy val tdd = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "tdd"
  )

parallelExecution in Test := true
