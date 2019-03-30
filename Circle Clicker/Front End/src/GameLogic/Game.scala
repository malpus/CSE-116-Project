package GameLogic

import GUI.app._

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class Game {

  val playerSpeed: Double = 10
  var radius1: Double =10

  var player1: player = new player
  var player2: player = new player

  val windowWidth: Double = 800
  val windowHeight: Double = 600

  //def updateBoundariesGame(width: Double, height: Double): Unit = {}

  def updateScoreBoard(): Unit = {
  }

  def EliminateUser(): Unit = {
    if (player1.points > player2.points) {
      sceneGraphics.children.remove(game.player1.circle)
    }
  }

  var TotalTime: Double = 0
  def update(deltaTime: Double): Unit = {

    TotalTime += deltaTime
    println(TotalTime)

    if (Math.abs(TotalTime -30) <.01) {
      EliminateUser()
      TotalTime = 0
    }

    updateScoreBoard()

    //updateBoundariesGame(circle)

    // update location circle
    // update positions?
  //update click totals (total points)
  }
}
