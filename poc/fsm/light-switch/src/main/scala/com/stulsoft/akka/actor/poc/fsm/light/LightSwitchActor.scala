/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.poc.fsm.light

import akka.actor.FSM

import scala.concurrent.duration._

/** Demonstrates usage of FSM actor
  *
  * @author Yuriy Stul
  */
class LightSwitchActor extends FSM[LightSwitchState, LightSwitchData] {

  startWith(Off, NoData)

  when(Off) {
    case Event(PowerOn, _) =>
      goto(On) using NoData
    case Event(PowerOff, _) =>
      println("already off")
      stay
  }

  when(On, stateTimeout = 1 second) {
    case Event(PowerOff, _) =>
      goto(Off) using NoData
    case Event(StateTimeout, _) =>
      println("'On' state timed out, moving to 'Off'")
      goto(Off) using NoData
  }

  whenUnhandled {
    case Event(e, s) =>
      log.warning("received unhandled request {} in state {}/{}", e, stateName, s)
      goto(Off) using NoData
  }

  onTransition {
    case Off -> On => println("Moved from Off to On")
    case On -> Off => println("Moved from On to Off")
  }

  initialize()
}
