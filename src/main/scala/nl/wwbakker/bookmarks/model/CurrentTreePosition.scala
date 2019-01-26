package nl.wwbakker.bookmarks.model

import nl.wwbakker.bookmarks.data.SerializerDeserializer

case class CurrentTreePosition(root : Root,
                               tileIdPath : List[TileId],
                               /*tileIdPath : List[TileId]*/) {

  private def currentCategory : Option[Category] =
    tileIdPath match {
      case Nil => None
      case _ =>
        root.atPath(tileIdPath) match {
          case c : Category => Some(c)
          case _ => None
        }
    }

  def nodeWithShortcut(shortcut: String) : Option[Node] =
    nodesWithId.find(_._2.shortcut == shortcut.toUpperCase).map(_._1)

  def nodesWithId : Seq[(Node, TileId)] =
    nodes.padTo(TileId.list.length, Empty).zip(TileId.list)

  def nodes : Seq[Node] =
    currentCategory.map(_.nodes).getOrElse(root.nodes)

  def drillDown(toCategory : TileId) : CurrentTreePosition =
    CurrentTreePosition(root, toCategory :: tileIdPath)

  def goBackUp : CurrentTreePosition =
    CurrentTreePosition(root, tileIdPath.drop(1))
}

object CurrentTreePosition {

  def initial: CurrentTreePosition = {
    SerializerDeserializer.default match {
      case Left(error) => println("decode from json error", error)
      case Right(result) => println("decode from json success", result)
    }
    CurrentTreePosition(
      SerializerDeserializer.default.toOption.get,
      Nil
    )
  }
}