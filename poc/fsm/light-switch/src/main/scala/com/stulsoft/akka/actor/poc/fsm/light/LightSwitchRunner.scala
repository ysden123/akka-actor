/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.poc.fsm.light

import akka.actor._

import scala.language.postfixOps

/**
  * @author Yuriy Stul
  */
object LightSwitchRunner extends App {

  runLightSwitch()

  def runLightSwitch(): Unit = {
    //create the actor system
    val system = ActorSystem("StateMachineSystem")

    val lightSwitchActor =
      system.actorOf(Props(classOf[LightSwitchActor]),
        "demo-LightSwitch")


    println("sending PowerOff, should be off already")
    lightSwitchActor ! PowerOff
    //akka is async allow it some time to pick up message
    //from its mailbox
    Thread.sleep(500)


    println("sending PowerOn")
    lightSwitchActor ! PowerOn
    //akka is async allow it some time to pick up message
    //from its mailbox
    Thread.sleep(500)

    println("sending PowerOff")
    lightSwitchActor ! PowerOff
    //akka is async allow it some time to pick up message
    //from its mailbox
    Thread.sleep(500)


    println("sending PowerOn")
    lightSwitchActor ! PowerOn
    //akka is async allow it some time to pick up message
    //from its mailbox
    Thread.sleep(500)

    println("sleep for a while to allow 'On' state to timeout")
    Thread.sleep(2000)

    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent.duration._
    system.scheduler.scheduleOnce(1.seconds) {
      //shutdown the actor system
      system.terminate()
    }
  }
}
