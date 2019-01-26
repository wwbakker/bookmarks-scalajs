package nl.wwbakker.bookmarks.model

object TileId {
  private val shortcuts : Seq[String] = "QWERTASDFG".split("")
  val list : Seq[TileId] =  shortcuts.zipWithIndex.map{ case (shortcut, index) => TileId.apply(shortcut, index)}

  def fromShortcut(shortcut : String) : TileId =
    list.find(_.shortcut == shortcut.toUpperCase).get

  implicit class TileIdSeqHelper[A](val seq : Seq[A]) extends AnyVal {
    def replacedAt(tileId: TileId, replacementNode: A): Seq[A] =
      seq.zipWithIndex.map {
        case (_, index) if tileId.index == index => replacementNode
        case (originalNode, _) => originalNode
      }
  }
}
case class TileId(shortcut : String, index : Int)