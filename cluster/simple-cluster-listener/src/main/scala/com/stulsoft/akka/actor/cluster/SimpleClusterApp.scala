/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.cluster

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

/**
  * @author Yuriy Stul
  */
object SimpleClusterApp extends App with LazyLogging {

  logger.info("Starting with {}", args)

  if (args.isEmpty)
    startup(Seq("2551", "2552", "0"))
  else
    startup(args)

  def startup(ports: Seq[String]): Unit = {
    ports foreach { port =>
      // Override the configuration of the port
      // To use artery instead of netty, change to "akka.remote.artery.canonical.port"
      // See https://doc.akka.io/docs/akka/current/remoting-artery.html for details
      val config = ConfigFactory.parseString(
        s"""
        akka.remote.netty.tcp.port=$port
        """).withFallback(ConfigFactory.load())

      logger.info("Port = {}, create actor for config: {}", port, config)

      // Create an Akka system
      val system = ActorSystem("ClusterSystem", config)
      // Create an actor that handles cluster domain events
      system.actorOf(Props[SimpleClusterListener], name = "clusterListener")
    }
  }
}
