package GameLogic

import GUI.app._

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class Game {

  val playerSpeed: Double = 10
  var radius1: Double =10

  var circle: Circle = new Circle {
    centerX = 20.0
    centerY = 50.0
    radius = radius1
    fill = Color.Green
  }

  val windowWidth: Double = 800
  val windowHeight: Double = 600

  //def updateBoundariesGame(width: Double, height: Double): Unit = {}

  def updatePoints(): Unit = {

  }

  def update(deltaTime: Double): Unit = {

    updateBoundariesGame(circle)

    // update location circle
    // update positions?
  //update click totals (total points)
  }



}
