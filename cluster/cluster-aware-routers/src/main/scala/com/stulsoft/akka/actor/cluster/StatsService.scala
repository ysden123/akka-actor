/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.cluster

import akka.actor.{Actor, Props}
import akka.routing.ConsistentHashingRouter.ConsistentHashableEnvelope
import akka.routing.FromConfig

/** The service that receives text from users and splits it up into words, delegates to workers and aggregates
  *
  * @author Yuriy Stul
  */
class StatsService extends Actor {
  // This router is used both with lookup and deploy of routees. If you
  // have a router with only lookup of routees you can use Props.empty
  // instead of Props[StatsWorker.class].
  private val workerRouter = context.actorOf(FromConfig.props(Props[StatsWorker]),
    name = "workerRouter")

  def receive: Receive = {
    case StatsJob(text) if text != "" =>
      val words = text.split(" ")
      val replyTo = sender() // important to not close over sender()
    // create actor that collects replies from workers
    val aggregator = context.actorOf(Props(
      classOf[StatsAggregator], words.size, replyTo))
      words foreach { word =>
        workerRouter.tell(
          ConsistentHashableEnvelope(word, word), aggregator)
      }
  }
}
