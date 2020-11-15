/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.pakka.basics.general.fail

import akka.actor.Actor
import akka.actor.Status.Failure

/**
  * @author Yuriy Stul
  */
class ActorFail1 extends Actor {
  override def receive: Receive = {
    case _ =>
      sender ! Failure(new RuntimeException("Test exception"))
  }
}
