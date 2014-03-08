package actors

import akka.actor.Actor
import models.Recipe
import akka.event.Logging

object DataStore {
  case class Persist(recipe: Recipe)
  case object Load
}

class DataStore extends Actor {

  val recipes: List[Recipe] = List(
    new Recipe("123", "Name1", "Category1", List("egg", "butter"), "Mix butter with egg.", 4, "url1")
  )

  import DataStore.{Persist, Load}

  val log = Logging(context.system, this)
  def receive: Receive = {
    case Persist(recipe: Recipe) =>
      ???
    case Load =>
      sender ! recipes

  }
}
