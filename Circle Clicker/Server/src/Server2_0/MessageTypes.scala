package Server2_0


// Received by Multiple Types
case object SendGameState
case class GameState(gameState: String)


// Received by GameActor
case object UpdateGame
case object EliminatePlayers
case class AddPlayer(username: String)
case class RemovePlayer(username: String)
case class MovePlayer(username: String, x: Double, y: Double)
case class Click(username: String, x: Double, y: Double)

