/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.tdd.failure

import akka.actor.Actor
import com.stulsoft.akka.tdd.{Msg2, Response2}

import scala.util.{Failure, Success, Try}

/**
 * @author Yuriy Stul
 */
final class ActorWithFailure extends Actor {
  override def receive: Receive = {
    case msg: Msg2 =>
      println(s"Msg2: $msg")
      handler(msg) match {
        case Success(result) =>
          println(s"Reply with $result")
          sender ! result
        case Failure(ex) =>
          sender ! akka.actor.Status.Failure(ex)
      }
    case x =>
      println(s"Unknown message: $x")
  }

  private def handler(msg: Msg2): Try[Response2] = {
    Try {
      if (msg.toFail)
        throw new RuntimeException("test exception")
      else
        Response2(msg.name + " edited")
    }
  }
}
