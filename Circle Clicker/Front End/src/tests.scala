import Client.GameLogic.Game
import org.scalatest.FunSuite
import scalafx.scene.shape.Circle

class tests extends FunSuite {
  test("Player Creation") {
    val game: Game = new Game
    assert(game.playerContainer.size == 0, "wrong")
    game.createPlayer("player1")
    assert(game.playerContainer.size == 1, "wrong")
    game.createPlayer("player2")
    assert(game.playerContainer.size == 2, "wrong")

  }

  test("Circle Creation"){
    val game: Game = new Game
    game.createPlayer("Player two")
    assert(game.playerContainer("Player two").circle.radius.value == 10, "wrong")
    game.createPlayer("Player three")
    game.playerContainer("Player three").circle.radius = 20
    assert(game.playerContainer("Player three").circle.radius.value == 20, "wrong")
  }

  test("Circle Elimination"){
    val game: Game = new Game
    game.createPlayer("Player two")
    game.createPlayer("Player three")
    assert(game.playerContainer.size == 2, "wrong")
    game.playerContainer("Player three").circle.radius = 20
    game.eliminateUser()
    assert(game.playerContainer.size == 1, "wrong")
    assert(game.playerContainer("Player two").circle.radius.value == 10, "wrong")
  }

}
