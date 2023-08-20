lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.2.4",
  scalaVersion := "3.3.0",
  libraryDependencies ++= {
    val akkaVersion = "2.8.4"
    val akkaHttpVersion = "10.5.2"
    val scalaLoggingVersion = "3.9.5"
    lazy val typeSafeConfVersion = "1.4.1"
    lazy val json4sVersion = "4.0.6"
    lazy val loggingVersion = "2.20.0"
    Seq(
      "org.json4s" %% "json4s-native" % json4sVersion,
      "org.json4s" %% "json4s-jackson" % json4sVersion,
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
      "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
      "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
      "com.typesafe" % "config" % typeSafeConfVersion
    )
  }
)

lazy val akkaHttpServer = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "akka-http-server"
  )