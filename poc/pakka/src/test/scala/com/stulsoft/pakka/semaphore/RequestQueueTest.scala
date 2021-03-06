package com.stulsoft.pakka.semaphore

import akka.actor.{ActorSystem, Props}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers

/**
  * Created by Yuriy Stul on 10/11/2016.
  */
class RequestQueueTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll {
  val system: ActorSystem = ActorSystem("system")
  val semaphoreActor = system.actorOf(Props(new SemaphoreActor(5)), "semaphoreActor")

  override def beforeAll(): Unit = {
    super.beforeAll
  }

  override def afterAll(): Unit ={
    system.stop(semaphoreActor)
    super.afterAll()
  }

  behavior of "ResourceQueue"
  "put" should "add new future" in {
    val resourceQueue: RequestQueue = new RequestQueue
    resourceQueue.put(ResourceRequest(semaphoreActor))
    val request = resourceQueue.get
    request.isEmpty should not be true
  }
  it should "add few requests" in {
    val resourceQueue: RequestQueue = new RequestQueue
    resourceQueue.put(ResourceRequest(semaphoreActor))
    resourceQueue.put(ResourceRequest(semaphoreActor))
    val request = resourceQueue.get
    request.isEmpty should not be true
    val request2 = resourceQueue.get
    request2.isEmpty should not be true
  }

  "get" should "return None for empty queue" in {
    val resourceQueue: RequestQueue = new RequestQueue
    val request = resourceQueue.get
    request.isEmpty should be(true)
  }

  it should "return future for non empty queue" in {
    val resourceQueue: RequestQueue = new RequestQueue
    resourceQueue.put(ResourceRequest(semaphoreActor))
    val request = resourceQueue.get
    request.isEmpty should be(false)
    resourceQueue.get.isEmpty should be(true)
  }
}
