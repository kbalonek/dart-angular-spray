package server

import akka.actor.Actor
import akka.event.LoggingReceive

class ServerSupervisor extends Actor with MainServer{
  def actorRefFactory = context
  def receive: Receive = LoggingReceive {
    runRoute(mainRoutes)
  }
}