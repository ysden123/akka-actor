package com.stulsoft.fsm2

import akka.actor.Actor
import math.min

/**
  * @author Yuriy Stul.
  */
class Publisher(totalNrBooks: Int, nrBooksPerRequest: Int)
  extends Actor {
  var nrLeft = totalNrBooks

  def receive = {
    case PublisherRequest =>
      if (nrLeft == 0)
        sender() ! BookSupplySoldOut // No more books left
      else {
        Thread.sleep(6000)
        val supply = min(nrBooksPerRequest, nrLeft)
        nrLeft -= supply
        sender() ! BookSupply(supply) // Supply a number of books
      }
  }
}