package Client.GameLogic

import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle


class Player(val name: String) {

  var points: Int = 0
  var playerSpeed: Double = Config.startingSpeed

  var circle: Circle = new Circle {
    val r = new scala.util.Random
    centerX_=(10 + r.nextInt((790-10) + 1))
    centerY_=(10 + r.nextInt((590-10) + 1))
    radius_=(Config.startingRadius)
    fill = Color.Green
  }

}
