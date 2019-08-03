lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.2.1",
  scalaVersion := "2.13.0",
  libraryDependencies ++= {
    val akkaVersion = "2.5.23"
    val akkaHttpVersion = "10.1.9"
    val scalaLoggingVersion = "3.9.2"
    val logbackClassicVersion = "1.2.3"
    lazy val typeSafeConfVersion = "1.3.4"
    lazy val json4sVersion = "3.6.7"
    Seq(
      "org.json4s" %% "json4s-native" % json4sVersion,
      "org.json4s" %% "json4s-jackson" % json4sVersion,
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
      "com.typesafe" % "config" % typeSafeConfVersion
    )
  }
)

lazy val akkaHttpServer = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "akka-http-server"
  )