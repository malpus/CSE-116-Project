package GameLogic

import GUI.app._

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class Game {

  val playerSpeed: Double = 10
  var radius1: Double =10

  var player1: player = new player

  val windowWidth: Double = 800
  val windowHeight: Double = 600

  //def updateBoundariesGame(width: Double, height: Double): Unit = {}

  def updatePoints(): Double = {
    player1.points
  }

  def updateScoreBoard(): Unit = {

    sceneGraphics.children.remove(game.player1.circle)
  }

  def EliminateUser(): Unit = {

  }

  def update(deltaTime: Double): Unit = {

    if (deltaTime == 10) {
      EliminateUser()
    }

    updateScoreBoard()

    updatePoints()

    //updateBoundariesGame(circle)

    // update location circle
    // update positions?
  //update click totals (total points)
  }
}
