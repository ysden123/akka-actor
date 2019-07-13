lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "1.2.0",
  scalaVersion := "2.12.8",
  libraryDependencies ++= {
    val akkaVersion = "2.5.3"
    val akkaHttpVersion = "10.1.8"
    val scalacticVersion = "3.0.8"
    val scalatestVersion = "3.0.8"
    val scalaLoggingVersion = "3.9.2"
    val logbackClassicVersion = "1.2.3"
    lazy val typeSafeConfVersion = "1.3.4"
    Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
      "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
      "com.typesafe" % "config" % typeSafeConfVersion,
      "org.scalactic" %% "scalactic" % scalacticVersion,
      "org.scalatest" %% "scalatest" % scalatestVersion % "test"
    )
  }
)

lazy val pakka = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "pakka"
  )