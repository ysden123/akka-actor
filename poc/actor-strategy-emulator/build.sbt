import sbt.Keys.libraryDependencies

lazy val akkaVersion = "2.6.13"
lazy val scalaMockTestSupportVersion = "3.6.0"
lazy val typeSafeConfVersion = "1.4.1"
lazy val scalaLoggingVersion = "3.9.3"
lazy val logbackClassicVersion = "1.2.3"
lazy val commonsIoVersion = "2.8.0"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.2",
  scalaVersion := "2.13.5",
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
    "commons-io" % "commons-io" % commonsIoVersion
  )
)

lazy val actorStrategyEmulator = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "actor-strategy-emulator"
  )

parallelExecution in Test := true
