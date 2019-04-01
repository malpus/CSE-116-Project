package Client.GameLogic

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class Player {

  var radiusStart: Double = 10

  var circle: Circle = new Circle {
    val r = new scala.util.Random
    centerX_=(10 + r.nextInt((790-10) + 1))
    centerY_=(10 + r.nextInt((590-10) + 1))
    radius_=(radiusStart)
    fill = Color.Green
  }

  var points: Int = 0

}
