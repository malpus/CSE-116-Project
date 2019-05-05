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

  test("Test click player"){
    var game = new Game()
    game.addPlayer("bob")
    game.addPlayer("sam")
    game.addPlayer("roger")
    game.addPlayer("caroline")
    assert(game.players("bob").score == 0, "check starting score")
    game.players("sam").x = 400
    game.players("sam").y = 900
    game.players("bob").x = 500
    game.players("bob").y = 400
    game.clickPlayer("bob",400,900)
    assert(game.players("sam").score == 1, "check click player method")
    game.clickPlayer("bob", 500, 400)
    assert(game.players("bob").score == 0, "check clicking on themselves")
  }

  test("Test Elimination"){
    var game = new Game()
    game.addPlayer("bob")
    game.addPlayer("john")
    game.addPlayer("mary")
    game.addPlayer("kate")
    game.players("bob").score =4
    game.EliminatePlayers()
    assert(game.players.size == 3, "check when same scores")
    assert(game.players.contains("bob") == false, "check elimination logic")
    game.EliminatePlayers()
    assert(game.players.size == 2, "still eliminates when equal amount")
  }
}