package GameLogic

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class player {

  var radius1: Double = 10

  var circle: Circle = new Circle {
    val r = new scala.util.Random
    centerX = 5 + r.nextInt((795-5) + 1)
    centerY = 5 + r.nextInt((595-5) + 1)
    radius = radius1
    fill = Color.Purple
  }

  var points: Int = 0

}
