
import java.awt.Color

import scala.swing.FileChooser.{SelectionMode, Result}
import scala.swing._
import scala.swing.event.ButtonClicked

class DirectoryWindow(title: String, width: Int, height: Int) {

  def deleteDirectory(): Unit = ???

  var directories: List[String] = List()

  val textBox = new ListView[String]() {
    listData = List.empty[String]
    preferredSize = new Dimension(width,height-100)
  }


  val fileDialog = new FileChooser()
  fileDialog.fileSelectionMode = SelectionMode.DirectoriesOnly

  val delDir = new Button("Remove Directory from scan") {
    reactions += {
      case ButtonClicked(_) =>

    }
  }


  val dirButton = new Button("Add Directory") {
    reactions += {
      case ButtonClicked(_) => {
        fileDialog.showOpenDialog(new Component {})
        // do check if they click cancel on selecting a file.
        if (fileDialog.selectedFile != null) {
          directories = directories ++ (List(fileDialog.selectedFile.getPath))
          refreshViews()
        }
        println(directories.toString())
      }
    }
  }



  val frame = new MainFrame {
    this.preferredSize = new Dimension(width,height)
    this.title = DirectoryWindow.this.title
  }


  def setup = {
    val panel = new FlowPanel() {
      contents += textBox
      contents += dirButton
      contents += delDir
    }
    this.frame.contents = panel
  }


    def refreshViews (): Unit = {
      textBox.listData = directories
    }


}
