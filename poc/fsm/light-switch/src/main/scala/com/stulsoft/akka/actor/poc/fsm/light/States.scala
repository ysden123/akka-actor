/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.poc.fsm.light

// states
sealed trait LightSwitchState
case object On extends LightSwitchState
case object Off extends LightSwitchState