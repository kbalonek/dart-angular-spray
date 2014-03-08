package application

import akka.actor._

import actors.Starter

object Application extends App {
  val system = ActorSystem("main-system")

  val starter: ActorRef = system.actorOf(Props[Starter], name = "main")

  starter ! Starter.Start
}