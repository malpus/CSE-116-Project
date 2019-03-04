package Server

class Game (val ID: Int){
  var playerList: Map[String, Player] = Map()

  def addPlayer(name: String): Unit = {
    val r = new scala.util.Random
    val randomX = r.nextInt(main.maxX)
    val randomY = r.nextInt(main.maxY)
    val player: Player = new Player(name)
    player.posX = randomX
    player.posY = randomY
    player.radius = main.defaultRadius
    playerList + (name -> player)
  }

  def updateData(): Unit = {} /** Will run at either 60hz or 30hz, sending a JSON udpate
  to each player in list, dead or alive */

  def verifyClick(mouseX: Double, mouseY: Double, attacker:String): Unit = {
    val x: Double = Math.pow(mouseX, 2)
    val y: Double = Math.pow(mouseY, 2)
    for ((name,data) <- playerList){
      if (Math.sqrt(Math.abs(x-Math.pow(data.posX,2)) + Math.abs(y-Math.pow(data.posY,2))) <= data.radius) {
        playerList(attacker).addPoint()
        playerList(name).radius += main.sizeChange
      }
    }
  }
}
