/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.states.counter

import akka.actor.{Actor, ActorLogging}

/** Demonstrates purely function state
  *
  * @author Yuriy Stul
  */
class MeanValueProcessor(threshold: Int) extends Actor with ActorLogging {
  log.info("threshold = {}", threshold)

  private val storage = Storage()

  override def receive: Receive = {
    case AddNumber(number) =>
      //      log.info("number = {}", number)
      if (storage.add(number) >= threshold) {
        log.info("Storage: {}", storage.list())
        log.info("Average: {}", storage.average())
        storage.clear()
      }
    case _: GetRest =>
      log.info("Rest: {}", storage.list())
  }
}
