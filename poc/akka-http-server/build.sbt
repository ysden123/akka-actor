lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.2.3",
  scalaVersion := "2.13.7",
  libraryDependencies ++= {
    val akkaVersion = "2.6.18"
    val akkaHttpVersion = "10.2.7"
    val scalaLoggingVersion = "3.9.4"
    lazy val typeSafeConfVersion = "1.4.1"
    lazy val json4sVersion = "4.0.3"
    lazy val loggingVersion = "2.17.0"
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