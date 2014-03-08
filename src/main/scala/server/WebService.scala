package server

import akka.util.Timeout
import scala.concurrent.duration._
import scala.language.postfixOps
import spray.routing.HttpService

trait WebService extends HttpService {
  implicit val executionContext = actorRefFactory.dispatcher

  implicit val timeout = Timeout(5 seconds)
}