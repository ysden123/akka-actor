/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.states.counter.become

import akka.actor.{Actor, ActorLogging}
import com.stulsoft.akka.actor.states.counter.{GetCache, RefreshCache}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/** Demo example of Cache actor
  *
  * @author Yuriy Stul
  */
class CacheActor extends Actor with ActorLogging {
  private def updated(data: Cache): Receive = {
    case RefreshCache =>
      log.info(s"Processing RefreshCache")
      val random = new Random()
      val newCacheValues = ArrayBuffer.empty[DataForCache]
      for (i <- 1 to 10) {
        val amount = random.nextInt()
        val newData = DataForCache(s"name $amount", amount)
        newCacheValues += DataForCache(s"name $amount", amount)
      }
      context.become(updated(Cache(newCacheValues.toSeq)))
    case GetCache =>
      log.info(s"Processing GetCache")
      sender ! data
    case x =>
      log.error(s"Unknown request $x")
  }

  override def receive: Receive = updated(Cache())
}

case class DataForCache(name: String, amount: Int)

case class Cache(data: Seq[DataForCache] = Seq.empty[DataForCache])