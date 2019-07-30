/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.states.counter

case class AddNumber(number:Int)
case object GetRest
case object RefreshCache
case object GetCache