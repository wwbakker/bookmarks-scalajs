package nl.wwbakker.bookmarks.components.nodecomponents

import nl.wwbakker.bookmarks.components.BookmarkNavigatorState
import nl.wwbakker.bookmarks.model.{Link, TileId}
import org.scalajs.dom
import org.scalajs.dom.raw.KeyboardEvent
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class LinkComponent extends StatelessComponent {

  case class Props(node : Link)
  override def render(): ReactElement =
    html.div(props.node.caption)

  //dom.window.location.replace(link.href)
}

object LinkComponent {
  def create(node : Link) : ReactElement =
    LinkComponent(node)

  def keyboardDownStateChange(initialState : BookmarkNavigatorState, e : KeyboardEvent, node : Link) : BookmarkNavigatorState =
    if (e.key == "Backspace")
      initialState.copy(tileIdPath = initialState.tileIdPath.drop(1))
    else {
      if (e.key == "Enter") {
        dom.window.location.replace(node.href)
      }
      initialState
    }
}
