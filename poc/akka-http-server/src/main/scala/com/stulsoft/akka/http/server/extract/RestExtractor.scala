/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.http.server.extract

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/** Extracts REST request parameters
 *
 * @author Yuriy Stul
 */
object RestExtractor extends App with StrictLogging {
  implicit val system: ActorSystem = ActorSystem()

  val route = get {
    pathSingleSlash {
      logger.info("Request for root page")
      getFromResource("index4.html", ContentTypes.`text/html(UTF-8)`)
    } ~
      path("addr1" / Segment / "addr2" / Segment) { (addr1Val, addr2Val) =>
        complete(extract(addr1Val, addr2Val))
        //                complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, response(s"addr1Val1 = $addr1Val, addr2Val1 = $addr2Val")))
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

  private def response(text: String): String = {
    s"""<html>
       |Response: $text
       |<br><br>
       |<a href="/">Start page</a>
       |</html>
    """.stripMargin
  }

  private def extract(addr1Val: String, addr2val: String): ToResponseMarshallable = {
    logger.info("==>extract")
    Thread.sleep(1000)
    HttpEntity(ContentTypes.`text/html(UTF-8)`, response(s"addr1Val = $addr1Val, addr2Val = $addr2val"))
  }
}
