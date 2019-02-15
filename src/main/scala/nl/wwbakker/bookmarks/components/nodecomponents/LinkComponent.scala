package nl.wwbakker.bookmarks.components.nodecomponents

import nl.wwbakker.bookmarks.model.{Link, TileId}
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class LinkComponent extends StatelessComponent {

  case class Props(node : Link,
                   changePathHandler : List[TileId] => Unit)
  override def render(): ReactElement =
    html.div(props.node.caption)

  //dom.window.location.replace(link.href)
}

object LinkComponent {
  def create(node : Link, changePathHandler : List[TileId] => Unit) : ReactElement =
    LinkComponent(node, changePathHandler)
}
