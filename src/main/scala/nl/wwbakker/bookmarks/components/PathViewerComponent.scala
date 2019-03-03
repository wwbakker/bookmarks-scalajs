package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.model.TileId
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class PathViewerComponent extends StatelessComponent {

  case class Props(tilePath: List[TileId])
  override def render(): ReactElement =
    html.div(("root" :: props.tilePath.map(_.shortcut)).mkString(" > "))
}

object PathViewerComponent {
  def create(tilePath: List[TileId]): ReactElement =
    PathViewerComponent(tilePath)
}