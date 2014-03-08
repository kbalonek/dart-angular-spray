package actors

import akka.actor.{ Actor, ActorRef, Props }
import akka.io.IO
import akka.routing.RoundRobinRouter
import spray.can.Http

import server.ServerSupervisor

object Starter {
  case object Start
}

class Starter extends Actor {
  import Starter.Start

  implicit val system = context.system

  def receive: Receive = {
    case Start =>
      val mainHandler: ActorRef =
        context.actorOf(Props[ServerSupervisor].withRouter(RoundRobinRouter(nrOfInstances = 10)))
      IO(Http) ! Http.Bind(mainHandler, interface = "localhost", port = 3000)
  }
}