package nl.wwbakker.bookmarks.components.nodecomponents

import nl.wwbakker.bookmarks.components.BookmarkNavigatorState
import nl.wwbakker.bookmarks.model.{Empty, Node, Root, TileId}
import org.scalajs.dom
import org.scalajs.dom.raw.KeyboardEvent
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class RootViewerComponent extends StatelessComponent {
  case class Props(root: Root) {
    def nodesWithId : Seq[(Node, TileId)] =
      root.nodes.padTo(TileId.list.length, Empty).zip(TileId.list)
  }

  override def render(): ReactElement = {
    val nodes = props.nodesWithId.map{case (node, tileId) => TileComponent.create(node, tileId)}
    html.div(
      html.className := "container-fluid tile-viewer mt-3 mb-3 h-100",
    )(
      nodes.grouped(nodes.length / 2).toSeq.zipWithIndex.map { case (row, index) =>
        html.div(html.className := "row h-25", html.key := s"card-row-$index")(row: _*)
      }
    )
  }
}

object RootViewerComponent {
  def create(root: Root) : ReactElement = RootViewerComponent(root) : ReactElement

  def keyboardDownStateChange(initialState : BookmarkNavigatorState, e : KeyboardEvent) : BookmarkNavigatorState =
    TileId.fromShortcut(e.key).map(tileId => initialState.copy(tileIdPath = initialState.tileIdPath :+ tileId)).getOrElse(initialState)
}