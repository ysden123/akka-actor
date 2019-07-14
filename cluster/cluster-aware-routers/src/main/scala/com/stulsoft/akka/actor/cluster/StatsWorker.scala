/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.cluster

import akka.actor.Actor

/** The worker that counts number of characters in each word
  *
  * @author Yuriy Stul
  */
class StatsWorker extends Actor {
  private var cache = Map.empty[String, Int]

  def receive:Receive = {
    case word: String =>
      val length = cache.get(word) match {
        case Some(x) => x
        case None =>
          val x = word.length
          cache += (word -> x)
          x
      }

      sender() ! length
  }
}