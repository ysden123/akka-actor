package com.stulsoft.akka.http.server.pathmatch

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.{LazyLogging, StrictLogging}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Tree matcher test 1.
 *
 * Demonstrates combinations with path parsing.
 *
 * Created by Yuriy Stul on 10/22/2016.
 */
object TreeMatcher1 extends App with StrictLogging {
  implicit val system: ActorSystem = ActorSystem()
  val route = get {
    pathSingleSlash {
      logger.info("Request for root page")
      getFromResource("index3.html", ContentTypes.`text/html(UTF-8)`)
    } ~
      pathPrefix("t1") {
        path("v1") {
          complete("t1 -> v1")
        } ~
          path("v2") {
            complete("t1 -> v2")
          }
      } ~
      path("t1") {
        complete("Just t1")
      } ~
      pathSuffix("t2") {
        complete("Just t2")
      } ~
      pathPrefix("t2") {
        path("v1") {
          complete("t2 -> v1")
        } ~
          path("v2") {
            complete("t2 -> v2")
          }
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
