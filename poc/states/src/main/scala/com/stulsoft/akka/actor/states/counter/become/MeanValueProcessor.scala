/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.states.counter.become

import akka.actor.{Actor, ActorLogging}
import com.stulsoft.akka.actor.states.counter.{AddNumber, GetRest}

/** Demonstrates purely function state with <i>become</i>
  *
  * @author Yuriy Stul
  */
class MeanValueProcessor(threshold: Int) extends Actor with ActorLogging {
  log.info("threshold = {}", threshold)

  private def updated(numbers: NumberCollection): Receive = {
    case AddNumber(number) =>
      log.info("number = {}", number)
      var newNumbers = NumberCollection(numbers.numbers :+ number)
      if (newNumbers.numbers.length >= threshold) {
        log.info("Numbers: {}", newNumbers.numbers)
        log.info("Average: {}", newNumbers.numbers.sum.toDouble / newNumbers.numbers.length)
        newNumbers = NumberCollection()
      }
      context.become(updated(newNumbers))
    case _: GetRest =>
      log.info("Rest: {}", numbers.numbers)
  }

  override def receive: Receive = updated(NumberCollection())
}

/**
  * State
  *
  * @param numbers
  */
case class NumberCollection(numbers: Seq[Int] = Seq.empty[Int])