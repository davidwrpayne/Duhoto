import java.awt.Color
import java.io.File
import javax.swing.ImageIcon

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.swing._

/**
  * Created by david.payne on 2/10/16.
  */
case class MatchWindow(matches: mutable.HashMap[String, ArrayBuffer[File]]) {



  def start = {
    val panel = new FlowPanel() {
      background = Color.RED

    }
    panel.contents += new Button("hello")

    matches.values.foreach({ matchArray =>
      val t = createPanelForMatch(matchArray)
      panel.contents += t
    })

    val top = new Frame() {
      contents = panel
      size = new Dimension(400, 400)

    }


    top.pack()

    top.visible = true

  }


  def createPanelForMatch(matchArray: ArrayBuffer[File]):Component = {
    val panel = new ListView[Label]()
    val som: ArrayBuffer[Label] =for(file <- matchArray) yield {
      val imageLabel = new Label() {
        icon = new ImageIcon(file.getPath()  )
      }
      imageLabel
    }

    panel.listData = som
    panel

  }
}
