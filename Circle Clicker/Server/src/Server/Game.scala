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
    playerList += (name -> player)
  }

  def updateData(): Unit = {} /** Will run at either 60hz or 30hz, using the socket that Jesse the plague has
  yet to teach us about, yet expected us to have a working API and server */

  def verifyClick(mouseX: Double, mouseY: Double, attacker: String): Unit = {
    val x: Double = Math.pow(mouseX, 2)
    val y: Double = Math.pow(mouseY, 2)
    for ((name,playerData) <- playerList){
      if (Math.sqrt(Math.abs(x-Math.pow(playerData.posX,2)) + Math.abs(y-Math.pow(playerData.posY,2))) <= playerData.radius) {
        playerList(attacker).addPoint()
        playerList(name).radius += main.sizeChange
      }
    }
  }
}
