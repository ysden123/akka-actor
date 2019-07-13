/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.cluster

import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{Actor, ActorRef, ActorSystem, Props, Terminated}
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.duration._
import scala.language.postfixOps

/** Sends to backend request to transform a text
  *
  * @author Yuriy Stul
  */
class TransformationFrontend extends Actor {

  private var backends = IndexedSeq.empty[ActorRef]
  private var jobCounter = 0

  def receive: Receive = {
    case job: TransformationJob if backends.isEmpty =>
      sender() ! JobFailed("Service unavailable, try again later", job)

    case job: TransformationJob =>
      jobCounter += 1
      backends(jobCounter % backends.size) forward job

    case BackendRegistration if !backends.contains(sender()) =>
      context watch sender()
      backends = backends :+ sender()

    case Terminated(a) =>
      backends = backends.filterNot(_ == a)
  }
}

object TransformationFrontend extends LazyLogging {
  def main(args: Array[String]): Unit = {
    // Override the configuration of the port when specified as program argument
    // To use artery instead of netty, change to "akka.remote.artery.canonical.port"
    // See https://doc.akka.io/docs/akka/current/remoting-artery.html for details
    val port = if (args.isEmpty) "0" else args(0)
    val config = ConfigFactory.parseString(
      s"""
        akka.remote.netty.tcp.port=$port
        """)
      .withFallback(ConfigFactory.parseString("akka.cluster.roles = [frontend]"))
      .withFallback(ConfigFactory.load())

    val system = ActorSystem("ClusterSystem", config)
    val frontend = system.actorOf(Props[TransformationFrontend], name = "frontend")

    val counter = new AtomicInteger
    import system.dispatcher
    // First 2 jobs are failed: service unavailable. At time sending message no one service (backend) registered yet.
    system.scheduler.schedule(2.seconds, 2.seconds) {
//    system.scheduler.schedule(10.seconds, 2.seconds) {  // All jobs succeeded
      implicit val timeout: Timeout = Timeout(5 seconds)
      (frontend ? TransformationJob("hello-" + counter.incrementAndGet())) onComplete (result =>
        result.foreach(transformationResult => logger.info("Transformation result: {}",
          transformationResult)))
    }

  }
}