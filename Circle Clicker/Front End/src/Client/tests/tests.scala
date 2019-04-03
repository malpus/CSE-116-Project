package Client.tests

import Client.GameLogic.{Game, Player}
import org.scalatest.FunSuite

class tests extends FunSuite {
  test("Player Creation") {
    val game: Game = new Game(new Player("self"))
    assert(game.playerContainer.isEmpty, "wrong")
    game.createPlayer("player1")
    assert(game.playerContainer.size == 1, "wrong")
    game.createPlayer("player2")
    assert(game.playerContainer.size == 2, "wrong")

  }

  test("Circle Creation"){
    val game: Game = new Game(new Player("self"))
    game.createPlayer("Player two")
    assert(game.playerContainer("Player two").circle.radius.value == 10, "wrong")
    game.createPlayer("Player three")
    game.playerContainer("Player three").circle.radius = 20
    assert(game.playerContainer("Player three").circle.radius.value == 20, "wrong")
  }

  test("Circle Elimination"){
    val game: Game = new Game(new Player("self"))
    game.createPlayer("Player two")
    game.createPlayer("Player three")
    assert(game.playerContainer.size == 2, "wrong")
    game.playerContainer("Player three").circle.radius = 20
    game.gameState.EliminatePlayer(true)
    assert(game.playerContainer.size == 1, "wrong")
    assert(game.playerContainer("Player two").circle.radius.value == 10, "wrong")
  }

}
