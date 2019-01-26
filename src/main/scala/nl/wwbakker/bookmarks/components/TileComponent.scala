package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.model.{Node, TileId}
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class TileComponent extends StatelessComponent {

  case class Props(node : Node, tileId : TileId)
  override def render(): ReactElement =
    html.div(html.className := s"tile m-1 ${props.node.className} card col")(
      html.div(html.className := "card-body")(
        html.span(html.className := "shortcut card-text")(props.tileId.shortcut),
        html.h5(html.className := "caption card-subtitle")(props.node.caption),
      ),
    )
}

