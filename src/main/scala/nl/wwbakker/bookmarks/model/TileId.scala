package nl.wwbakker.bookmarks.model

object TileId {
  private val shortcuts : Seq[String] = "QWERTASDFG".split("")
  val list : Seq[TileId] =  shortcuts.zipWithIndex.map{ case (shortcut, index) => TileId.apply(shortcut, index)}

  def fromShortcut(shortcut : String) : TileId =
    list.find(_.shortcut == shortcut.toUpperCase).get

  implicit class TileIdSeqHelper[A](val seq : Seq[A]) extends AnyVal {
    def replacedAt(tileId: TileId, replacementNode: A): Seq[A] =
      seq.zipWithIndex.printValue.map { case (originalNode, index) =>
        if (tileId.index == index)
          replacementNode
        else
          originalNode
      }
  }


  implicit class PrintValueHelper[A](val a : A) extends AnyVal {
    def printValue : A = {
      System.out.println(a.toString)
      a
    }
  }
}
case class TileId(shortcut : String, index : Int)