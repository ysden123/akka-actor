/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.pakka.basics.general.fail

import java.util.concurrent.Executors

import scala.util.{Failure, Success}

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * @author Yuriy Stul
  */
object FailEx1 extends App {
  implicit val timeout: Timeout = Timeout(60.seconds)
  implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(50))

  val system = ActorSystem("testPoison")
  val actor = system.actorOf(Props(new ActorFail1))
  val future = (actor ? "Test message")
  println(s"future: $future")
  future.onComplete {
    case Success(s) =>
      println(s"Success: $s")
//          system.terminate()
    case Failure(e) =>
      println(s"Failure: $e")
//          system.terminate()
  }

  future.onComplete(_ => {
    println("Terminating actor system...")
    system.terminate()
    sys.exit(0)
  })
}
