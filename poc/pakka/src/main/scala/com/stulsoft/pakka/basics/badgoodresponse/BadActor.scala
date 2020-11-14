/*
 * Copyright (c) 2020. StulSoft
 */

package com.stulsoft.pakka.basics.badgoodresponse

import akka.actor.Status.Failure
import akka.actor.{Actor, ActorLogging}


/**
 * @author Yuriy Stul
 */
class BadActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case msg:String =>
      sender ! Failure(new RuntimeException(s"Test exception for $msg"))
  }
}
