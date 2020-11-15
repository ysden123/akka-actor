/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.pakka.timeout

import akka.pattern.ask
import akka.actor.{ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.duration._
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await

/** Sends future with ask pattern (?)
  *
  * @author Yuriy Stul
  */
object LongServiceRunner extends LazyLogging {
  def main(args: Array[String]): Unit = {
    logger.info("==>main")
    val system = ActorSystem("system")

    val longService = system.actorOf(Props(new LongService), "longService")

    // Timeout is less than long service duration (4 seconds)
    implicit val timeout: Timeout = Timeout(3.seconds)
    val future = longService ? "test"

    try {
      val result = Await.result(future, timeout.duration)
      logger.info("Result is {}", result)
    } catch {
      case ex: Exception => logger.error("Failed getting reply. {}", ex.getMessage)
    } finally {
      Thread.sleep(3000)
      system.terminate()
    }

    logger.info("<==main")
  }
}
