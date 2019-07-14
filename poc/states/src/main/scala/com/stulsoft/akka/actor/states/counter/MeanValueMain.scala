/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.states.counter

import java.util.concurrent.CountDownLatch

import akka.actor.{ActorSystem, Props}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._

/**
  * @author Yuriy Stul
  */
object MeanValueMain extends LazyLogging {

  def main(args: Array[String]): Unit = {
    logger.info("==>main")
    val first1 = 1
    val last1 = 6
    val first2 = 7
    val last2 = 13
    val count = new CountDownLatch(last1 + last2 - first1 - first2 + 2)
    logger.info("count = {}", count.getCount)
    val system = ActorSystem("meanValueSystem")

    val processor = system.actorOf(Props(new MeanValueProcessor(5)), "meanProcessor")

    import system.dispatcher

    def schedule(first: Int, last: Int): Unit = {
      system.scheduler.scheduleOnce(1.seconds) {
        (first to last).foreach(i => {
          logger.info("{} - {}}: {}", first, last, i)
          processor ! AddNumber(i)
          count.countDown()
        })
      }
    }

    schedule(first1, last1)
    schedule(first2, last2)
    count.await()
    system.scheduler.scheduleOnce(0.seconds) {
      processor ! GetRest()
      system.scheduler.scheduleOnce(1.seconds) {
        system.terminate()

        logger.info("<==main")
      }
    }
  }
}
