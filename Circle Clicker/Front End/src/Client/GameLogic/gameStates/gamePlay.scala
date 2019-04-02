package Client.GameLogic.gameStates
import Client.GameLogic.Game
import javafx.scene.input.KeyCode

class gamePlay(game: Game) extends gameState {
  override def keyInput(keyCode: KeyCode): Unit = {
        keyCode.getName match {
      case "Up" => game.client.circle.centerY_=(game.client.circle.centerY.value-game.playerSpeed)
      case "Left" => game.client.circle.centerX_=(game.client.circle.centerX.value-game.playerSpeed)
      case "Down" => game.client.circle.centerY_=(game.client.circle.centerY.value+game.playerSpeed)
      case "Right" => game.client.circle.centerX_=(game.client.circle.centerX.value+game.playerSpeed)
    }
  }

  override def mouseInput(): Unit = {}
}
