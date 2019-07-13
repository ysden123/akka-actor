/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.cluster

final case class TransformationJob(text: String)
final case class TransformationResult(text: String)
final case class JobFailed(reason: String, job: TransformationJob)
case object BackendRegistration
