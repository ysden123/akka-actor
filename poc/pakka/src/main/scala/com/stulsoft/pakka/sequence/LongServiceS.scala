/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.pakka.sequence

import scala.concurrent.duration._
import akka.actor.{Actor, ActorLogging}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}
import scala.util.Random

/**
  * @author Yuriy Stul
  */
class LongServiceS extends Actor with ActorLogging {
  private val random = Random
  implicit val ec: ExecutionContextExecutor = ExecutionContext.global

  override def receive: Receive = {
    case msg:String =>
      val theSender = sender()
      log.info("handling {}", msg)
      context.system.scheduler.scheduleOnce((1 + random.nextInt(5)).seconds){
        log.info("Sending reply for {} ...", msg)
        theSender ! s"Done for $msg"
      }
  }
}
