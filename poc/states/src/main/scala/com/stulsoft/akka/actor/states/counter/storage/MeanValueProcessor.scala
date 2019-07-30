/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.states.counter.storage

import akka.actor.{Actor, ActorLogging}
import com.stulsoft.akka.actor.states.counter.{AddNumber, GetRest}

/** Demonstrates purely function state without <i>become</i>
  *
  * @author Yuriy Stul
  */
class MeanValueProcessor(threshold: Int) extends Actor with ActorLogging {
  log.info("threshold = {}", threshold)

  private val storage = Storage()

  override def receive: Receive = {
    case AddNumber(number) =>
      log.info("number = {}", number)
      if (storage.add(number) >= threshold) {
        log.info("Storage: {}", storage.list())
        log.info("Average: {}", storage.average())
        storage.clear()
      }
    case GetRest =>
      log.info("Rest: {}", storage.list())
  }
}
