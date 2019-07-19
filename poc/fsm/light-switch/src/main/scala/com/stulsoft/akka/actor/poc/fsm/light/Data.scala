/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.poc.fsm.light

//data
sealed trait LightSwitchData
case object NoData extends LightSwitchData