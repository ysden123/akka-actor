import sbt.Keys.libraryDependencies

lazy val akkaVersion = "2.5.23"
lazy val scalatestVersion = "3.0.8"
lazy val typeSafeConfVersion = "1.3.4"
lazy val scalaLoggingVersion = "3.9.2"
lazy val logbackClassicVersion = "1.2.3"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.3",
  scalaVersion := "2.13.0",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-remote" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
    "com.typesafe" % "config" % typeSafeConfVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
    "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion % "test",
    "org.scalatest" %% "scalatest" % scalatestVersion % "test"
  )
)
lazy val poolRouter = project.in(file("pool-router"))
  .settings(commonSettings)
  .settings(
    name := "pool-router"
  )

lazy val groupRouter = project.in(file("group-router"))
  .settings(commonSettings)
  .settings(
    name := "group-router"
  )

parallelExecution in Test := true
