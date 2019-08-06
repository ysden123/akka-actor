/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.poc.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/** Flow based variant of Akka HTTP client
 *
 * @author Yuriy Stul
 */
object FlowBasedVariant extends App with LazyLogging {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val connectionFlow =
    Http().outgoingConnectionHttps("www.smugmug.com")
  val request = HttpRequest(uri = "/")
  val responseFuture = Source.single(request)
    .via(connectionFlow)
    .runWith(Sink.head)

  responseFuture.andThen {
    case Success(response) =>
      response
        .entity
        .toStrict(5.seconds)
        .map(_.data.decodeString("UTF-8"))
        .andThen {
          case Success(body) =>
            logger.info(s"response: $body")
            logger.info(s"Status code: ${response.status}")
          case Failure(ex) =>
            logger.error(s"Failed getting body: ${ex.getMessage}")
        }
        .andThen {
          case _ =>
            response.discardEntityBytes()
            materializer.shutdown()
            system.terminate()
        }
    case Failure(ex) =>
      logger.error(s"Failed getting response: ${ex.getMessage}")
      exit(-1)
  }

  def exit(code: Int): Unit = {
    materializer.shutdown()
    system.terminate()
    sys.exit(code)
  }

}
