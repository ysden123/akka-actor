/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.routing.pool.router

import akka.actor.{Actor, ActorLogging}

import scala.util.Random

/**
 * @author Yuriy Stul
 */
class Actor2  extends Actor with ActorLogging {
  private val n = self.path.name
  override def receive: Receive = {
    case x =>
      log.info(s"Actor2 ($n): received $x")
      Thread.sleep(Random.nextInt(1000))
      log.info(s"Actor2 ($n): completed msg handling")
      sender ! s"Done by Actor2 ($n)"
  }

  override def preStart(): Unit = {
    super.preStart()
    log.info(s"Staring Actor2 ($n)")
  }

  override def postStop(): Unit = {
    super.postStop()
    log.info(s"Stopping $n")
  }
}
