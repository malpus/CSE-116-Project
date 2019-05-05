package tests

import org.scalatest._
import Server._
import Server2_0.Game

class TestGame extends FunSuite{
  test("Test Add and Remove Player"){
    var game = new Game()
    assert(game.players.isEmpty == true, "doesn't start empty")
    game.addPlayer("bob")
    assert(game.players.size == 1, "didn't add player")
    game.addPlayer("sam")
    game.addPlayer("rory")
    game.addPlayer("another one")
    assert(game.players.size == 4, "check amount of players added")
    assert(game.players("bob").x < 1000, "check x location")
    assert(game.players("rory").y < 500, "check y location")
    game.removePlayer("another one")
    assert(game.players.size == 3, "check remove method")
  }

  test("Test verifyClick"){}

  test("Test Update"){}
}