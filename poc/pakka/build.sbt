lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.2.1",
  scalaVersion := "2.13.3",
  libraryDependencies ++= {
    val akkaVersion = "2.6.10"
    val akkaHttpVersion = "10.2.1"
    val scalacticVersion = "3.2.3"
    val scalatestVersion = "3.2.3"
    val scalaLoggingVersion = "3.9.2"
    lazy val typeSafeConfVersion = "1.4.1"
    lazy val loggingVersion = "2.14.0"
    Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "org.apache.logging.log4j" % "log4j-api" % loggingVersion,
      "org.apache.logging.log4j" % "log4j-core" % loggingVersion,
      "org.apache.logging.log4j" % "log4j-slf4j-impl" % loggingVersion,
      "com.typesafe" % "config" % typeSafeConfVersion,
      "org.scalactic" %% "scalactic" % scalacticVersion,
      "org.scalatest" %% "scalatest" % scalatestVersion % Test
    )
  }
)

lazy val pakka = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pakka"
  )