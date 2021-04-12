/*
 * Copyright (c) 2021. Webpals
 */

package com.stulsoft.http.client.test

import com.typesafe.scalalogging.StrictLogging

import java.net.URI
import java.net.http.HttpRequest.BodyPublishers
import java.net.http.HttpResponse.BodyHandlers
import java.net.http.{HttpClient, HttpRequest}
import java.time.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future, Promise}

/**
 * @author Yuriy Stul
 */
object TestClient2 extends App with StrictLogging {
  logger.info("==>TestClient2")

  val future = test()

  Await.result(future, scala.concurrent.duration.Duration.Inf)
  logger.info("<==TestClient2")

  def test(): Future[Unit] = {
    val promise = Promise[Unit]()
    Future {
      read("http://localhost:8080/rest/long")
        .onComplete(_ => read("http://localhost:8080/rest/long2")
          .onComplete(_ => {
            logger.info("Completed")
            promise.success()
          }))
    }
    promise.future
  }

  def read(uri: String): Future[Unit] = {
    logger.info("==>read")

    val promise = Promise[Unit]()
    val client = HttpClient.newBuilder()
      .build()

    val request = HttpRequest.newBuilder()
      .uri(URI.create(uri))
      .timeout(java.time.Duration.ofSeconds(2))
      .POST(BodyPublishers.ofString("test request"))
      .build()

    try {
      client.sendAsync(request, BodyHandlers.ofString())
        .thenAccept(response => {
          logger.info("statusCode = {}", response.statusCode())
          logger.info("response body: {}", response.body())
          promise.success()
        })
        .get()
    } catch {
      case exception: Exception =>
        logger.error(exception.getMessage)
        promise.failure(exception)
    }

    promise.future
  }
}
