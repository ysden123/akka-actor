/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.routing.pool.router

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.routing.FromConfig
import akka.util.Timeout
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.io.StdIn

/**
 * @author Yuriy Stul
 */
object Main extends App with LazyLogging {
  start()

  def start(): Unit = {
    logger.info("Started Main")
    val system = ActorSystem("poolRouterSystem")
    // @formatter:off
    val router1 = system.actorOf(           // Defines router using configuration
      FromConfig.props(Props(new Actor1)),  // How router should create routees
      "poolRouter1"                 // Name of router (see conf file)
    )

    val router2 = system.actorOf(            // Defines router using configuration
      FromConfig.props(Props(new Actor2)),   // How router should create routees
      "poolRouter2"                  // Name of router (see conf file)
    )
    // @formatter:on

    (1 to 10).foreach(i => router1 ! s"msg $i")

    implicit val timeout: Timeout = Timeout(5.seconds)
    implicit val ec: ExecutionContext = system.dispatchers.defaultGlobalDispatcher
    (1 to 10).foreach(i => (router2 ? s"msg $i").foreach { result => logger.info(s"Received reply $result") })

    println("Enter line to exit")
    StdIn.readLine()
    system.terminate()
    logger.info("Finished Main")
  }
}
