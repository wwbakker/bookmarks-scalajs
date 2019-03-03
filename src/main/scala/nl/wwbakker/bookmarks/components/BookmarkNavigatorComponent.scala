package nl.wwbakker.bookmarks.components

import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html
import nl.wwbakker.bookmarks.components.nodecomponents._
import nl.wwbakker.bookmarks.model.{Category, Link, Root, TileId}
import org.scalajs.dom
import org.scalajs.dom.raw.KeyboardEvent

@react class BookmarkNavigatorComponent extends Component {
  type Props = Unit
  type State = BookmarkNavigatorState

  override def initialState: BookmarkNavigatorState = BookmarkNavigatorState.initial

  dom.document.onkeydown = { e: KeyboardEvent =>
    setState((initialState: BookmarkNavigatorState) =>
      initialState.root.atPath(initialState.tileIdPath) match {
        case _: Category => CategoryViewerComponent.keyboardDownStateChange(initialState, e)
        case _: Root => RootViewerComponent.keyboardDownStateChange(initialState, e)
        case l: Link => LinkComponent.keyboardDownStateChange(initialState, e, l)
        case _ => initialState
      })
  }

  override def render(): ReactElement =
    html.div(html.className := "container-fluid tile-viewer mt-3 mb-3 h-100")(
      PathViewerComponent.create(state.tileIdPath),
      state.root.atPath(state.tileIdPath) match {
        case c: Category => CategoryViewerComponent.create(state.tileIdPath, c)
        case r: Root => RootViewerComponent.create(r)
        case l: Link => LinkComponent.create(l)
        case _ => html.div()
      }
    )
}

