package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.model.{Category, Node}
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class TileComponent extends StatelessComponent {

  case class Props(node : Node, shortcut : String)
  override def render(): ReactElement =
    html.div(html.className := s"tile ${props.node.className}")(
      html.span(html.className := "caption")(props.node.caption),
      html.span(html.className := "shortcut")(props.shortcut),
    )
}

