package Server

class Player (name: String){
  var posX: Double = 0
  var posY: Double = 0
  var radius: Double = 0
  var score: Double = 0 //Radius is score with a multiplier attached
  var address: String = ""
  var alive: Boolean = true

  def addPoint(): Unit = {
    score += 1
  }
}
