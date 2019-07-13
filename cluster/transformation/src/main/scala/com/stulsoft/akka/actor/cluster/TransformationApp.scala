/*
 * Copyright (c) 2019. Yuriy Stul
 */

package com.stulsoft.akka.actor.cluster

import com.typesafe.scalalogging.LazyLogging

/**
  * @author Yuriy Stul
  */
object TransformationApp extends App with LazyLogging {
  // starting 2 frontend nodes and 3 backend nodes
  TransformationFrontend.main(Seq("2551").toArray)
  TransformationBackend.main(Seq("2552").toArray)
  TransformationBackend.main(Array.empty)
  TransformationBackend.main(Array.empty)
  TransformationFrontend.main(Array.empty)
}
