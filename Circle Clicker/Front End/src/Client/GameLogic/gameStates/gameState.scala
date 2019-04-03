package Client.GameLogic.gameStates

import javafx.scene.input.KeyCode

trait gameState {
  def keyInput(key: KeyCode): Unit

  def mouseInput(mouseX: Double, mouseY: Double): Unit

  def update(dt: Double): Unit

  def EliminatePlayer(debug: Boolean): Unit

  def main(): Unit
}
