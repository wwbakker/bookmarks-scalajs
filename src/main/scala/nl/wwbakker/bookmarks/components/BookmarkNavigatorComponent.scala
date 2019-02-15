package nl.wwbakker.bookmarks.components

import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html
import nl.wwbakker.bookmarks.components.nodecomponents._
import nl.wwbakker.bookmarks.model.{Category, Link, Root, TileId}

@react class BookmarkNavigatorComponent extends Component {
  type Props = Unit
  type State = BookmarkNavigatorState

  override def initialState: BookmarkNavigatorState = BookmarkNavigatorState.initial

  private def setTileIdPath : List[TileId] => Unit =
    tileIdPath => setState(state.copy(tileIdPath = tileIdPath))

  override def render(): ReactElement =
    html.div(html.className := "container-fluid tile-viewer mt-3 mb-3 h-100")(
      state.root.atPath(state.tileIdPath) match {
        case c : Category =>
          CategoryViewerComponent.create(state.tileIdPath, c, setTileIdPath)
        case r : Root =>
          RootViewerComponent.create(state.root, setTileIdPath)
        case l : Link =>
          LinkComponent.create(l, setTileIdPath)
        case _ => html.div()
      }

    )
}

