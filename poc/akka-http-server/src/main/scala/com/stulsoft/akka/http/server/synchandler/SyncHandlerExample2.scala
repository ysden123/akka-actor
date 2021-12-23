package com.stulsoft.akka.http.server.synchandler

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.{LazyLogging, StrictLogging}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Usage high-level server side API
  *
  * Created by Yuriy Stul on 10/21/2016.
  */
object SyncHandlerExample2 extends App with StrictLogging {
  implicit val system: ActorSystem = ActorSystem()
  val route = get {
    pathSingleSlash {
      getFromResource("index2.html", ContentTypes.`text/html(UTF-8)`)
    } ~
      path("ping") {
        complete("PONG!")
      } ~
      path("crash") {
        logger.error("BOOM!")
        sys.error("BOOM!")
      } ~
      path("favicon.ico") {
        complete("")
      } ~
      path("stop") {
        Future {
          Thread.sleep(500)
          system.terminate()
          logger.info("Stopped")
          sys.exit(0)
        }
        complete("Stopped")
      }
  }

  Http().newServerAt("localhost", 8080).bind(route)
  println("Started. Try http://localhost:8080")
  logger.info("Started")
}
