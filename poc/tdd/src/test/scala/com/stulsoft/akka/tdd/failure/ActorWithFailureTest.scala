/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.tdd.failure

import akka.actor.Status.Failure
import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import akka.util.Timeout
import com.stulsoft.akka.tdd.{Msg2, Response2, StopSystemAfterAll}
import org.scalatest.FunSuiteLike

import scala.concurrent.duration._

/**
 * @author Yuriy Stul
 */
class ActorWithFailureTest extends TestKit(ActorSystem("testsystem"))
  with FunSuiteLike
  with ImplicitSender
  with StopSystemAfterAll {
  val toFail = true
  val noFail = false
  implicit val timeout: Timeout = Timeout(5.seconds)

  test("without exception") {
    val actor = system.actorOf(Props(new ActorWithFailure()))
    val srcName = "initial name"
    actor ! Msg2(srcName, noFail)
    expectMsg(Response2(srcName + " edited"))
  }

  test("with exception 1") {
    val actor = system.actorOf(Props(new ActorWithFailure()))
    val srcName = "initial name"
    actor ! Msg2(srcName, toFail)
    expectMsgAnyClassOf(classOf[Failure])
  }

  test("with exception 2") {
    val actor = system.actorOf(Props(new ActorWithFailure()))
    val srcName = "initial name"
    actor ! Msg2(srcName, toFail)
    expectMsgPF() {
      case Failure(ex) if ex.getMessage == "test exception" => ()
    }
  }
}
