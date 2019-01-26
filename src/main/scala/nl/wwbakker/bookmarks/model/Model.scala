package nl.wwbakker.bookmarks.model
import TileId.TileIdSeqHelper

sealed trait Node {
  def caption : String
  def className : String
  def atPath(tileIdPath : List[TileId]) : Node =
    tileIdPath match {
      case Nil => this
      case _ => throw new IllegalStateException("Cannot find node. Invalid path.")
    }
  def withNodeReplacedAt(tileIdPath : List[TileId], replacementNode : Node) : Node =
    throw new IllegalStateException("Cannot replace node: Unable to find path.")
}

case class Category(caption: String,
                    nodes: Seq[Node],
                   ) extends Node {
  def className = "category border-warning"

  def withNodeReplacedAt(tileId : TileId, replacementNode : Node) : Node =
    withNodes(replacementNodes = nodes.replacedAt(tileId, replacementNode))

  override def withNodeReplacedAt(tileIdPath : List[TileId], replacementNode : Node) : Node = tileIdPath match {
    case Nil => throw new IllegalStateException("Cannot replace node: Unable to find path.")
    case tileId :: Nil => withNodeReplacedAt(tileId, replacementNode)
    case tileId :: rest => withNodeReplacedAt(rest, nodes(tileId.index).withNodeReplacedAt(rest, replacementNode))
  }

  def withNodes(replacementNodes: Seq[Node]): Category = copy(nodes = replacementNodes)

  override def atPath(tileIdPath : List[TileId]) : Node = tileIdPath match {
    case Nil => this
    case x :: xs => nodes(x.index).atPath(xs)
  }
//  def apply(tileId: TileId) : Option[Node] = if (nodes.isDefinedAt(tileId.index)) Some(nodes(tileId.index)) else None

}

case class Link(caption: String,
                href: String,
               ) extends Node {
  def className = "link border-primary"
}

case object Empty extends Node {
  def caption = ""
  def className = "empty"
}

case class Root(nodes: Seq[Node]) {
  def withNodes(replacementNodes: Seq[Node]): Root = copy(replacementNodes)

  def atPath(categoryIdPath: List[TileId]) : Node = categoryIdPath match {
    case head :: rest => nodes(head.index).atPath(rest)
    case _ => throw new IllegalArgumentException("Path is empty.")
  }

  def withNodeReplacedAt(tileId : TileId, replacementNode : Node) : Root =
    withNodes(replacementNodes = nodes.replacedAt(tileId, replacementNode))

  def withNodeReplacedAt(tileIdPath : List[TileId], replacementNode : Node) : Root = tileIdPath match {
    case Nil => throw new IllegalStateException("Cannot replace node: Path points to the root.")
    case tileId :: Nil => withNodeReplacedAt(tileId, replacementNode)
    case tileId :: rest => withNodeReplacedAt(rest, nodes(tileId.index).withNodeReplacedAt(rest, replacementNode))
  }
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