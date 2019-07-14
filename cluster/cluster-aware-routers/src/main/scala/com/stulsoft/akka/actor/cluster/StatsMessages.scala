/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.cluster

final case class StatsJob(text: String)

final case class StatsResult(meanWordLength: Double)

final case class JobFailed(reason: String)
