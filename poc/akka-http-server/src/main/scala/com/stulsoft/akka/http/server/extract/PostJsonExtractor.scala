/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.http.server.extract

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.typesafe.scalalogging.StrictLogging
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.{read, write}

/** Extracts POST request in JSON format
 *
 * @author Yuriy Stul
 */
object PostJsonExtractor extends App with StrictLogging {
  implicit val system: ActorSystem = ActorSystem("PostJsonExtractor")
  implicit val formats: DefaultFormats = DefaultFormats

  val route = post {
    pathSingleSlash {
      entity(as[String]) { json =>
        try {
          val request = read[SimpleRequest](json)
          val response = SimpleResponse(request.name + " EDITED", request.points + 1000)
          complete(HttpEntity(ContentTypes.`application/json`, write(response)))
        } catch {
          case ex: Exception =>
            complete(StatusCodes.BadRequest, ex.getMessage)
        }
      }
    }
  }

  Http().newServerAt("localhost", 8080).bind(route)
  println("Started. Try http://localhost:8080")
  logger.info("Started")
}
