package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.model._
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.ul
import org.scalajs.dom

@react class BookmarkNavigatorComponent extends Component {
  type Props = Unit
  type State = CurrentTreePosition

  dom.document.onkeydown = { e : dom.KeyboardEvent =>
    if (e.key == "Backspace") {
      setState(state.goBackUp)
    }else {
      state.nodeWithShortcut(e.key)
        .collect(categoryOnly)
        .foreach(category => setState(state.drillDown(category)))
    }
  }
  private def filterCategory(n : Node) : Option[Category] = n match {
    case c : Category => Some(c)
    case _ => None
  }

  private def categoryOnly : PartialFunction[Node, Category] = {
    case c : Category => c
  }

  override def initialState: CurrentTreePosition = CurrentTreePosition.initial

  def createCC(node: Node) : ReactElement =
    CategoryComponent(node = node).withKey(state.nodeKey(node))

  override def render(): ReactElement = ul(
    state.children.map(createCC)
  )
}


case class CurrentTreePosition(root : Root,
                               currentPath : List[Category]) {

  def nodeWithShortcut(shortcut : String) : Option[Node] =
    children.find(_.shortcut.toUpperCase == shortcut.toUpperCase)

  def children : Seq[Node] =
    currentPath match {
      case Nil =>
        root.children
      case mostSpecificCategory :: _ =>
        mostSpecificCategory.children
    }

  def nodeKey(node : Node) : String =
    currentPath.map(_.shortcut).mkString + node.shortcut


  def drillDown(toCategory : Category) : CurrentTreePosition =
    CurrentTreePosition(root, toCategory :: currentPath)

  def goBackUp : CurrentTreePosition =
    CurrentTreePosition(root, currentPath.drop(1))
}

object CurrentTreePosition {
  def initial = CurrentTreePosition(
    Bookmarks.example,
    Nil
  )
}