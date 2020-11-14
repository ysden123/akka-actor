/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.pakka.basics.badgoodresponse

import akka.Done
import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success}

/**
 * @author Yuriy Stul
 */
object BadGoodApp extends App with StrictLogging {
  logger.info("==>BadGoodApp")
  val system = ActorSystem("BadGoodApp")
  val goodActor = system.actorOf(Props(new GoodActor), "goodActor")
  val badActor = system.actorOf(Props(new BadActor), "badActor")
  implicit val timeout: Timeout = Timeout(10.seconds)

  testGood()
    .map(_ => testBad())
    .onComplete(_ => system.terminate())

  def testBad(): Future[Done] = {
    val promise = Promise[Done]
    badActor.ask("Test message 2")
      .onComplete {
        case Success(response) =>
          logger.info(s"Response from bad actor: $response")
          promise.success(Done)
        case Failure(f) =>
          logger.error(s"Failure from bad actor: ${f.getMessage}")
          promise.success(Done)
      }
    promise.future
  }

  def testGood(): Future[Done] = {
    val promise = Promise[Done]
    goodActor.ask("Test message 1")
      .onComplete {
        case Success(response: String) =>
          logger.info(s"Response from good actor: $response")
          promise.success(Done)
      }
    promise.future
  }
}
