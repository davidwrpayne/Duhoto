import scala.swing._


object PhotoDuplicate extends SimpleSwingApplication {

  println("Starting PhotoDuplicate")
  val resolution = (800,600)
//  val scannedWindow = None
  val dirWindow = new DirectoryWindow("Duplicate Photo Finder: Choose Directories to Scan",resolution._1,resolution._2)

  dirWindow.setup

  override def top: Frame = dirWindow.frame


}
