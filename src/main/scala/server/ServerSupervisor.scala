package server

import akka.actor.Actor

class ServerSupervisor extends Actor with MainServer{
  def actorRefFactory = context
  def receive: Receive = runRoute(mainRoutes)
}