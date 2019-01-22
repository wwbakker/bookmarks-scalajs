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
        }

    }
  }

  override def initialState: CurrentTreePosition = CurrentTreePosition.initial

  def createTileComponent(node: Node, shortcut: String) : ReactElement =
    TileComponent(node = node, shortcut = shortcut).withKey(shortcut)



  override def render(): ReactElement =
    html.div(html.className := "bookmark-navigator-component")(
      state.nodesWithShortcuts.map{case (node, shortcut) => createTileComponent(node, shortcut)}
    )
}

object Shortcuts {
  val all : Seq[String] = "QWERTASDFG".split("")
}

case class CurrentTreePosition(root : Root,
                               currentPath : List[Category]) {


  def nodeWithShortcut(shortcut: String) : Option[Node] =
    nodesWithShortcuts.find(_._2 == shortcut.toUpperCase).map(_._1)

  def nodesWithShortcuts : Seq[(Node, String)] =
    nodes.zip(Shortcuts.all)

  def nodes : Seq[Node] =
    currentPath match {
      case Nil =>
        root.categories
      case mostSpecificCategory :: _ =>
        mostSpecificCategory.nodes
    }

  def drillDown(toCategory : Category) : CurrentTreePosition =
    CurrentTreePosition(root, toCategory :: currentPath)

  def goBackUp : CurrentTreePosition =
    CurrentTreePosition(root, currentPath.drop(1))
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