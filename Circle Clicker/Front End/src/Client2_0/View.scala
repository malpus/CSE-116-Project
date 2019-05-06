package Client2_0

import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import javafx.application.Platform
import javafx.scene.input.{KeyEvent, MouseEvent}
import play.api.libs.json.Json
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

class HandleMessagesFromPython() extends Emitter.Listener {
  override def call(args: Object*): Unit = {
    Platform.runLater(() => {
      val gameStateList: List[String] = Json.parse(args.apply(0).toString).as[List[String]]
      var circleList: Map[String, Map[String, String]] = Map()
      for (i <- gameStateList){
        val player: Map[String, String] = Json.parse(i).as[Map[String, String]]
        var circle: Map[String, String] = Map(
          "x" -> player("posx"),
          "y" -> player("posy"),
          "score" -> player("score"),
          "color" -> "Red"
        )
        if (DesktopGUI.clientID == player("pid")){
          circle = circle + ("color" -> "Green")
        }
        circleList += (player("pid") -> circle)
      }
      DesktopGUI.newServerCircleList = circleList
    })
  }
}

class grabClientID() extends Emitter.Listener{
  override def call(args: Object*): Unit = {
    Platform.runLater(() => {
      if (args.nonEmpty && DesktopGUI.clientID == "") {
        val message: String = args.apply(1).toString
        DesktopGUI.clientID = message
      }
    })
  }
}

object DesktopGUI extends JFXApp {
  var socket: Socket = IO.socket("http://localhost:8080/")
  socket.on("gameState", new HandleMessagesFromPython)
  socket.on("connect", new grabClientID)

  socket.connect()
  socket.emit("connect")

  var clientID: String = ""

  var lastUpdateTime: Long = System.nanoTime()

  var newServerCircleList: Map[String, Map[String, String]] = Map()
  var currentServerCircleList: Map[String, Circle] = Map()

  val stageGroup: Group = new Group {}

  this.stage = new PrimaryStage {
    title = "Project: Circle Clicker"
    scene = new Scene() {
      content = List(stageGroup)
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => socket.emit("keystates", event.getCode))
      addEventHandler(MouseEvent.MOUSE_CLICKED, (event: MouseEvent) => socket.emit("click", Json.stringify(Json.toJson(Map("x" -> event.getX, "y" -> event.getY)))))
    }
  }

  val update: Long => Unit = (time: Long) => {
    val dt: Double = (time - lastUpdateTime) / 1000000000.0
    lastUpdateTime = time
    var childrenMap: Map[String, Circle] = Map()
    var childrenList: List[Circle] = List()
    for ((i, j) <- newServerCircleList){
      if (currentServerCircleList.contains(i)){
        currentServerCircleList(i).centerX_=(j("x").toDouble)
        currentServerCircleList(i).centerY_=(j("y").toDouble)
        currentServerCircleList(i).radius_=(j("score").toDouble * 5 + 10)
        childrenMap = childrenMap + (i -> currentServerCircleList(i))
        childrenList = currentServerCircleList(i) :: childrenList
      } else {
        val Circle: Circle = new Circle{
          centerX_=(j("x").toDouble)
          centerY_=(j("y").toDouble)
          radius_=(j("score").toDouble * 5 + 10)
          if (j("color") == "Red"){
            fill = Color.Red
          } else {
            fill = Color.Green
          }
        }
        childrenMap = childrenMap + (i -> Circle)
        childrenList = Circle :: childrenList
      }
    }
    stageGroup.children_=(childrenList)
    currentServerCircleList = childrenMap
  }
  AnimationTimer(update).start()
}