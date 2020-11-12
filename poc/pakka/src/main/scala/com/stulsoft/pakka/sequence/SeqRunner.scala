/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.pakka.sequence

import akka.pattern.ask
import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

/**
  * @author Yuriy Stul
  */
object SeqRunner extends StrictLogging {
  def main(args: Array[String]): Unit = {
    logger.info("==>main")

    val system = ActorSystem("SequenceTest")
    val longServiceS = system.actorOf(Props(new LongServiceS), "longServiceS")

    implicit val timeout: Timeout = Timeout(10.seconds)

    for (i <- 1 to 10) {
      val f = longServiceS ? s"message # $i"
      f.onComplete {
        case Success(result) =>
          logger.info("Response: {}", result)
        case Failure(exception) =>
          logger.error(exception.getMessage)
      }
    }
  }
}
