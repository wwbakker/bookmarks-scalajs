package nl.wwbakker.bookmarks.model
import TileId.TileIdSeqHelper

sealed trait Node {
  def caption : String
  def className : String
  def withNodeReplacedAt(tileIdPath : List[TileId], replacementNode : Node) : Node
}

trait ReplaceableNodes[Self] {
  this: Self =>

  def nodes : Seq[Node]
  def withNodes(nodes : Seq[Node]) : Self

  def withNodeReplacedAt(tileId : TileId, replacementNode : Node) : Self =
    withNodes(nodes = nodes.replacedAt(tileId, replacementNode))

  def withNodeReplacedAt(tileIdPath : List[TileId], replacementNode : Node) : Self = tileIdPath match {
    case Nil => this
    case tileId :: Nil => withNodeReplacedAt(tileId, replacementNode)
    case tileId :: rest => withNodeReplacedAt(tileId, nodes(tileId.index).withNodeReplacedAt(rest, replacementNode))
  }
}

case class Category(caption: String,
                    nodes: Seq[Node],
                   ) extends Node with ReplaceableNodes[Category] {
  def className = "category border-warning"

  override def withNodes(replacementNodes: Seq[Node]): Category = copy(nodes = replacementNodes)

//  def apply(tileId: TileId) : Option[Node] = if (nodes.isDefinedAt(tileId.index)) Some(nodes(tileId.index)) else None

}

case class Link(caption: String,
                href: String,
               ) extends Node {
  def className = "link border-primary"
  def withNodeReplacedAt(tileIdPath : List[TileId], replacementNode : Node) : Link = this
}

case object Empty extends Node {
  def caption = ""
  def className = "empty"
  def withNodeReplacedAt(tileIdPath : List[TileId], replacementNode : Node) : Node = this
}

case class Root(nodes: Seq[Node]) extends ReplaceableNodes[Root] {
  override def withNodes(replacementNodes: Seq[Node]): Root = copy(replacementNodes)
}

object Bookmarks {
  def example =
    Root(
      Seq(
        Category(
          caption = "Search Engines",
          nodes = Seq(
            Link(
              caption = "Google",
              href = "https://www.google.nl"
            ),
            Link(
              caption = "Bing",
              href = "https://www.bing.com"
            )
          ))
        )
      )
}