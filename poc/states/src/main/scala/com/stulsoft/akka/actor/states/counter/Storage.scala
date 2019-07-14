/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.states.counter

import scala.collection.mutable.ListBuffer

/**
  * @author Yuriy Stul
  */
case class Storage() {
  private val numbers = ListBuffer.empty[Int]

  /**
    * Adds a number and returns size of collection
    *
    * @param number the number to add
    * @return the size of collection
    */
  def add(number: Int): Int = {
    numbers += number
    numbers.size
  }

  def clear(): Unit = {
    numbers.clear()
  }

  def average(): Double = {
    if (numbers.nonEmpty)
      numbers.sum.toDouble / numbers.size
    else
      0.0
  }

  def list(): IndexedSeq[Int] = {
    numbers.toIndexedSeq
  }
}