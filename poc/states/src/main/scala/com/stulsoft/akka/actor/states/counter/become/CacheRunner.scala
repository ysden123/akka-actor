/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.states.counter.become

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.stulsoft.akka.actor.states.counter.{GetCache, RefreshCache}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await

/**
  * @author Yuriy Stul
  */
object CacheRunner extends LazyLogging {
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("CacheSystem")
    val cacheActor = system.actorOf(Props(new CacheActor()), "cache")

    import system.dispatcher

    import scala.concurrent.duration._
    implicit val timeout: Timeout = Timeout(5 seconds)

    system.scheduler.schedule(0.seconds, 3.seconds, cacheActor, RefreshCache)

    def showCache(): Unit = {
      val cacheFuture = cacheActor ? GetCache
      val result = Await.result(cacheFuture, timeout.duration).asInstanceOf[Cache]
      logger.info(s"Cache: $result")
    }

    system.scheduler.scheduleOnce(0.seconds) {
      showCache()
    }

    system.scheduler.scheduleOnce(1.seconds) {
      showCache()
    }

    system.scheduler.scheduleOnce(5.seconds) {
      showCache()
      system.terminate()
    }

  }
}
