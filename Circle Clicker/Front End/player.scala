package GameLogic

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class player {

  var radius1: Double = 10

  var circle: Circle = new Circle {
    centerX = 20.0
    centerY = 50.0
    radius = radius1
    fill = Color.Green
  }

  var points: Int = 0

}
