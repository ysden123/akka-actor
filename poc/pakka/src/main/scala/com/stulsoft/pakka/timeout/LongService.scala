/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.pakka.timeout

import scala.concurrent.duration._
import akka.actor.{Actor, ActorLogging}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

/** A long service with delay in 4 seconds
  *
  * @author Yuriy Stul
  */
class LongService extends Actor with ActorLogging {
  override def receive: Receive = {
    case "test" =>
      log.info("received test")
      implicit val ec: ExecutionContextExecutor = ExecutionContext.global

      context.system.scheduler.scheduleOnce(4.seconds){
        log.info("Sending reply...")
        sender() ! "Done"
      }
    case _ => log.info("received unknown message")
  }
}
