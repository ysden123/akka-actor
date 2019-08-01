/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.tdd

final case class Msg1(name: String)

final case class Msg2(name: String, toFail: Boolean)