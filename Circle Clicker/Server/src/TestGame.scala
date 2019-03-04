package tests

import org.scalatest._
import Server.Game.{verifyClick, addPlayer}
import Server.main

class TestGame extends FunSuite{
  test("Test Mouse Detection"){
    val game: Game = new Game(1)
    val player1: Player = game.addPlayer("testPlayer1")
    player1.posX = 10
    player1.posY = 10
    val player2: Player = game.addPlayer("testPlayer2")
    val mouse1XT: Double = 10.0
    val mouse1YT: Double = 11.0
    
    assert(player1.score == 0)
    assert(player2.score == 0)
    assert(player1.radius == 5)
    assert(player2.radius == 5)
    
    verifyClick(mouse1XT, mouse1YT, "testPlayer2")
    
    assert(player1.score == 0)
    assert(player2.score == 1)
    assert(player1.radius == 6)
    assert(player2.radius == 5)
    
    player1.posX = 0
    player1.posY = 0
    
    verifyClick(mouse1XT, mouse1YT, "testPlayer2")
    
    assert(player1.score == 0)
    assert(player2.score == 1)
    assert(player1.radius == 6)
    assert(player2.radius == 5)
  }
}
