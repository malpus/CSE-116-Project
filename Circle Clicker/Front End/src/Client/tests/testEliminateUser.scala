package Client.tests

import Client.GameLogic.{Game, Player}
import Client.client.{game, _}
import org.scalatest._

class testEliminateUser extends FunSuite {

  var playerContainer: Map[String, Player] = Map()

  def createPlayer(name: String): Unit = {
    playerContainer += (name -> new Player(name))
  }


  def EliminateUser(): Unit = {
    var highestRadius: Double = 0
    var name: String = ""
    for ((i, j) <- playerContainer) {
      if (j.circle.radius.value > highestRadius) {
        highestRadius = j.circle.radius.value
        name = i
      }
    }
    sceneGraphics.children.remove(game.playerContainer(name).circle)
    playerContainer = playerContainer - name
  }


  test("CreatePlayer") {
    val game = new Game(new Player("self"))


    game.createPlayer("Evan")
    game.createPlayer("Tyler")
    println(game.playerContainer)

    game.playerContainer.apply("Evan") != game.playerContainer.apply("Tyler")
  }

  test("test EliminatePlayer") {
  val game = new Game(new Player("self"))

  game.createPlayer("Evan")
  game.createPlayer("Tyler")
  game.playerContainer.apply("Evan").circle.radius.value = 10

    game.playerContainer.apply("Tyler").circle.radius.value = 5
    game.playerContainer.size == 1
  }
}