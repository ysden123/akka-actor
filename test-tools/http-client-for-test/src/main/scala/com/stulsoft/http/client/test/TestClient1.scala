/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.http.client.test

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.settings.ConnectionPoolSettings
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.{ExecutionContextExecutor, Future, Promise}
import scala.util.{Failure, Success}

/**
 * @author Yuriy Stul
 */
object TestClient1 extends App with StrictLogging {
  implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "TestClient1")
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.executionContext

  println(s"(1) idleTimeout=${ConnectionPoolSettings(system).connectionSettings.idleTimeout}")
  println(s"(1) connectingTimeout=${ConnectionPoolSettings(system).connectionSettings.connectingTimeout}")

  test1("http://localhost:8080/rest/long")
    .onComplete(_ => test1("http://localhost:8080/rest/long2")
      .onComplete(_ => system.terminate()))

  def test1(uri: String): Future[Unit] = {
    val promise = Promise[Unit]()
    val request = HttpRequest(
      method = HttpMethods.POST,
      uri,
      entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, "test data")
    )
    logger.info("Send request")
    val start = System.currentTimeMillis()

    val responseFuture = Http().singleRequest(request)
    responseFuture.onComplete {
      case Success(res) =>
        val duration = System.currentTimeMillis() - start
        logger.info("Response status = {}", res.status)
        Unmarshal(res.entity).to[String].onComplete {
          case Success(response) =>
            logger.info("Response: {}", response)
            logger.info("Response received in {} ms", duration)
            res.discardEntityBytes()
          case Failure(exception) =>
            logger.error("Failed extracting response: {}", exception.getMessage)
            res.discardEntityBytes()
        }
        promise.success()
      case Failure(ex) =>
        logger.error(ex.getMessage)
        promise.success()
    }
    promise.future
  }
}
