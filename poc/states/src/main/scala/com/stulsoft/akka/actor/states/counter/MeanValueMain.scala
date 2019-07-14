/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.states.counter

import akka.actor.{ActorSystem, Props}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._

/**
  * @author Yuriy Stul
  */
object MeanValueMain extends LazyLogging {

  def main(args: Array[String]): Unit = {
    logger.info("==>main")
    val system = ActorSystem("meanValueSystem")

    val processor = system.actorOf(Props(new MeanValueProcessor(5)), "meanProcessor")


    import system.dispatcher

    system.scheduler.scheduleOnce(1.seconds) {
      (1 to 6).foreach(i => processor ! AddNumber(i))
    }

    system.scheduler.scheduleOnce(1.seconds) {
      (7 to 13).foreach(i => processor ! AddNumber(i))
    }

    system.scheduler.scheduleOnce(5.seconds) {
      processor ! GetRest()
    }


    system.scheduler.scheduleOnce(10.seconds) {
      system.terminate()

      logger.info("<==main")
    }
  }
}
