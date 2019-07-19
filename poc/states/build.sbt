import sbt.Keys.libraryDependencies

lazy val akkaVersion = "2.5.23"
lazy val scalatestVersion = "3.0.8"
lazy val scalaMockTestSupportVersion = "3.6.0"
lazy val typeSafeConfVersion = "1.3.4"
lazy val scalaLoggingVersion = "3.9.2"
lazy val logbackClassicVersion = "1.2.3"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.1",
  scalaVersion := "2.12.8",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
    "com.typesafe" % "config" % typeSafeConfVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion % "test",
    "org.scalatest" %% "scalatest" % scalatestVersion % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % scalaMockTestSupportVersion % "test"
  )
)

lazy val clusterAwareRouters = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "states"
  )


parallelExecution in Test := true
