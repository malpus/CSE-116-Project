package Client.GameLogic

object Config {

  /** Just a list of starting values that the server will provide when implemented.*/

  val startingRadius: Double = 10 /**Radius all players start with*/
  val startingSpeed: Double = 20 /**Speed all players start with*/
  val EliminationTime: Double = 5 /**How often the largest player gets eliminated*/
  val deltaRadius: Double = 1 /**Change of radius per click*/
  val selfHarm: Boolean = true /**Whether the player can click on themselves or not*/
  val warmUpTime: Double = 10

}
