/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.routing.group.router

import akka.actor.{ActorSystem, Props}
import akka.routing.FromConfig
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn

/**
 * @author Yuriy Stul
 */
object Main extends App with LazyLogging {
  start()

  def start(): Unit = {
    logger.info("Started Main")
    val system = ActorSystem("groupRouterSystem")
    system.actorOf(Props(new Actor1Creator(2)), "Actor1Creator") // Creates route creator
    val router = system.actorOf(FromConfig.props(), "groupRouter") // Creates a group router

    (1 to 10).foreach(i => router ! s"msg $i")

    println("Enter line to exit")
    StdIn.readLine()
    system.terminate()
    logger.info("Finished Main")
  }
}
