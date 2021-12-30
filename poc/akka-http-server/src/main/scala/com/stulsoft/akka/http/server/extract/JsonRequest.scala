/*
 * Copyright (c) 2021. StulSoft
 */

package com.stulsoft.akka.http.server.extract

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.typesafe.scalalogging.StrictLogging
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.read

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}
/**
 * @author Yuriy Stul
 */
object JsonRequest extends App with StrictLogging {
  implicit val system: ActorSystem = ActorSystem("PostJsonExtractor")
  implicit val formats: DefaultFormats = DefaultFormats
  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

  sendRequest()

  def sendRequest(): Unit = {
    Http().singleRequest(
      HttpRequest(
        method = HttpMethods.POST,
        uri = "http://localhost:8080",
        entity = HttpEntity(ContentTypes.`application/json`,
          """{
            |"name": "test name",
            |"points": 123
            |}
            |""".stripMargin)
      ))
      .onComplete {
        case Success(res) =>
          Unmarshal(res.entity).to[String].onComplete{
            case Success(response) =>
              logger.info("Response is {}", response)
              val simpleResponse = read[SimpleResponse](response)
              logger.info("SimpleResponse: {}", simpleResponse)
              res.discardEntityBytes()
              system.terminate()
            case Failure(exception) =>
              logger.error(exception.getMessage, exception)
              system.terminate()
          }
        case Failure(exception) =>
          logger.error(exception.getMessage, exception)
          system.terminate()
      }
  }
}
