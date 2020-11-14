/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.pakka.basics.badgoodresponse

import akka.actor.Status.Success
import akka.actor.{Actor, ActorLogging}

/**
 * @author Yuriy Stul
 */
class GoodActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg:String =>
      sender ! Success(s"Response for $msg")
  }
}
