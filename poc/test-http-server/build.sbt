lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.0.3",
  scalaVersion := "2.13.4",
  libraryDependencies ++= {
    val akkaVersion = "2.6.10"
    val akkaHttpVersion = "10.2.1"
    val scalaLoggingVersion = "3.9.2"
    val logbackClassicVersion = "1.2.3"
    lazy val typeSafeConfVersion = "1.3.3"
    lazy val scalaToolsVersion = "1.3.1"
    lazy val loggingVersion = "2.14.0"
    Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe" % "config" % typeSafeConfVersion,
      "com.stulsoft" %% "ys-scala-tools" % scalaToolsVersion,

      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
      "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
      "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion

    )
  }
)

lazy val testHttpServer = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "test-http-server"
  )