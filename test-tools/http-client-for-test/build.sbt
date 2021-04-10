lazy val akkaHttpVersion = "10.2.4"
lazy val akkaVersion = "2.6.14"
lazy val scalaLoggingVersion = "3.9.3"
lazy val loggingVersion = "2.14.1"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.stulsoft",
      scalaVersion := "2.13.5"
    )),
    name := "http-client-for-test",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
      "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
      "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion
    )
  )
