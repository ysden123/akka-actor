package com.stulsoft.pakka.semaphore

import akka.actor.ActorRef

import scala.collection.immutable.Queue

/**
  * Resource future
  *
  * Contains sender of a future.
  *
  * @param sender sender of the future.
  */
case class ResourceRequest(sender: ActorRef)

/**
  * Created by Yuriy Stul on 10/11/2016.
  *
  * Manages future queue
  */
class RequestQueue {
  private var queue: Queue[ResourceRequest] = Queue[ResourceRequest]()

  /**
    * Gets a resource future from queue
    *
    * @return the resource future, if exits
    */
  def get: Option[ResourceRequest] = {
    if (queue.isEmpty)
      None
    else {
      val (request: ResourceRequest, q: Queue[ResourceRequest]) = queue.dequeue
      queue = q
      Some(request)
    }
  }

  /**
    * Puts a resource future into queue
    *
    * @param request the resource future to put
    */
  def put(request: ResourceRequest): Unit = {
    require(request != null, "future is undefined")
    queue = queue.enqueue(request)
  }
}
