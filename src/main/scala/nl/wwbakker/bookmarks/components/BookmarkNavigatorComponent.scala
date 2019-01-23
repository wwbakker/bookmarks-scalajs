package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.data.SerializerDeserializer
import nl.wwbakker.bookmarks.model._
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html
import org.scalajs.dom

@react class BookmarkNavigatorComponent extends Component {
  type Props = Unit
  type State = CurrentTreePosition

  dom.document.onkeydown = { e : dom.KeyboardEvent =>
    if (e.key == "Backspace") {
      setState(state.goBackUp)
    }else {
      state.nodeWithShortcut(e.key)
        .foreach {
          case category: Category => setState(state.drillDown(category))
          case link: Link => dom.window.location.replace(link.href)
          case _ => ()
        }

    }
  }

  override def initialState: CurrentTreePosition = CurrentTreePosition.initial

  def createTileComponent(node: Node, shortcut: String) : ReactElement =
    TileComponent(node = node, shortcut = shortcut).withKey(shortcut)



  override def render(): ReactElement = {
    val nodes = state.nodesWithShortcuts.map{case (node, shortcut) => createTileComponent(node, shortcut)}
    html.div(html.className := "container-fluid bookmark-navigator-component mt-3 mb-3 h-100")(
      nodes.grouped(nodes.length / 2).toSeq.zipWithIndex.map { case (row, index) =>
        html.div(html.className := "row h-25", html.key := s"card-row-$index")(row: _*)
      }
    )
  }

}

object Shortcuts {
  val all : Seq[String] = "QWERTASDFG".split("")
}

case class CurrentTreePosition(root : Root,
                               categoryPath : List[Category],
                               /*tileIdPath : List[TileId]*/) {

  private def currentCategory : Option[Category] = categoryPath.headOption
//  private def currentTileId : Option[TileId] = tileIdPath.headOption

  def nodeWithShortcut(shortcut: String) : Option[Node] =
    nodesWithShortcuts.find(_._2 == shortcut.toUpperCase).map(_._1)

  def nodesWithShortcuts : Seq[(Node, String)] =
    nodes.padTo(Shortcuts.all.length, Empty).zip(Shortcuts.all)

  def nodes : Seq[Node] =
    currentCategory.map(_.nodes).getOrElse(root.nodes)

  def drillDown(toCategory : Category) : CurrentTreePosition =
    CurrentTreePosition(root, toCategory :: categoryPath)

  def goBackUp : CurrentTreePosition =
    CurrentTreePosition(root, categoryPath.drop(1))
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