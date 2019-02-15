package nl.wwbakker.bookmarks.components.nodecomponents

import nl.wwbakker.bookmarks.model._
import org.scalajs.dom
import org.scalajs.dom.raw.KeyboardEvent
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class CategoryViewerComponent extends StatelessComponent {

  case class Props(tilePath: List[TileId],
                   currentCategory: Category,
                   changePathHandler: List[TileId] => Unit) {
    def nodesWithId: Seq[(Node, TileId)] =
      currentCategory.nodes.padTo(TileId.list.length, Empty).zip(TileId.list)
  }


  def handleKeydown : dom.Event => Unit = e =>
      if (e.asInstanceOf[KeyboardEvent].key == "Backspace") {
        props.changePathHandler(props.tilePath.drop(1))
      } else {
        props.changePathHandler(TileId.fromShortcut(e.asInstanceOf[KeyboardEvent].key) :: props.tilePath)
      }

  override def render(): ReactElement = {
    val nodes = props.nodesWithId.map { case (node, tileId) => TileComponent.create(node, tileId) }
    html.div(
      html.className := "container-fluid tile-viewer mt-3 mb-3 h-100",
      html.tabIndex := 0,
      html.onKeyDown := handleKeydown,
    )(
      nodes.grouped(nodes.length / 2).toSeq.zipWithIndex.map { case (row, index) =>
        html.div(html.className := "row h-25", html.key := s"card-row-$index")(row: _*)
      }
    )
  }
}

object CategoryViewerComponent {
  def create(tilePath: List[TileId],
             currentCategory: Category,
             changePathHandler: List[TileId] => Unit): ReactElement =
    CategoryViewerComponent(tilePath, currentCategory, changePathHandler)
}