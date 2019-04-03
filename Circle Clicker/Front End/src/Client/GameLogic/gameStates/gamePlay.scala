package Client.GameLogic.gameStates

import Client.GameLogic.{Config, Game}
import Client.client.sceneGraphics
import javafx.scene.input.KeyCode

class gamePlay(game: Game) extends gameState {
  override def keyInput(keyCode: KeyCode): Unit = {
        keyCode.getName match {
      case "Up"|"W" => game.client.circle.centerY_=(game.client.circle.centerY.value-game.client.playerSpeed)
      case "Left"|"A" => game.client.circle.centerX_=(game.client.circle.centerX.value-game.client.playerSpeed)
      case "Down"|"S" => game.client.circle.centerY_=(game.client.circle.centerY.value+game.client.playerSpeed)
      case "Right"|"D" => game.client.circle.centerX_=(game.client.circle.centerX.value+game.client.playerSpeed)
    }
  } /**Translates key presses into actions according to the play state*/

  override def mouseInput(mouseX: Double, mouseY: Double): Unit = {
    for ((i, j) <- game.playerContainer) {
      val x: Double = j.circle.centerX.value
      val y: Double = j.circle.centerY.value
      val radius: Double = j.circle.radius.value
      val clickDistance: Double = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2))
      if (clickDistance <= radius) {
        game.playerContainer(i).points += 1
        game.playerContainer(i).circle.radius_=(Config.deltaRadius + radius)
      }
    }
    if (Config.selfHarm){
      val x: Double = game.client.circle.centerX.value
      val y: Double = game.client.circle.centerY.value
      val radius: Double = game.client.circle.radius.value
      val clickDistance: Double = Math.sqrt(Math.pow(mouseX - x, 2) + Math.pow(mouseY - y, 2))
      if (clickDistance <= radius) {
        game.client.circle.radius_=(Config.deltaRadius + game.client.circle.radius.value)
      }
    }
  } /**Iterates through live players to see who was hit*/

  override def update(dt: Double): Unit = {
    game.ElapsedTime += dt
    if (Math.abs(game.ElapsedTime - Config.EliminationTime) < .01) {
      EliminatePlayer()
      game.ElapsedTime = 0
    }
  } /**Calls EliminatePlayer every @Config.EliminationTime seconds*/

  override def EliminatePlayer(debug: Boolean = false): Unit = {
    var highestRadius: Double = 0
    var names: List[String] = List()
    for ((i, j) <- game.playerContainer){
      if (j.circle.radius.value > highestRadius){
        highestRadius = j.circle.radius.value
        names = List(i)
      } else if (j.circle.radius.value == highestRadius){
        names = i :: names
      }
    }
    if (game.client.circle.radius.value >= highestRadius){
      game.gameState = new gameLoss(game)
      game.gameState.main()
    } else if (names != List()) {
      for (i <- names) {sceneGraphics.children.remove(game.playerContainer(i).circle)}
      if (!debug) {for (i <- names) {game.playerContainer = game.playerContainer - i}}
    }
    if (game.playerContainer.isEmpty){
      game.gameState = new gameWon(game)
      game.gameState.main()
    }
  }/** Eliminates the highest radius player *//**WILL SOON BE DEPRECATED*/

  override def main(): Unit = {
    game.ElapsedTime = 0
    game.instantiatePlayers()
    println("Game Start!")
  }
}
