package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.model._
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html
import org.scalajs.dom

@react class BookmarkNavigatorComponent extends Component {
  type Props = Unit
  type State = CurrentTreePosition

  dom.document.onkeydown = { e : dom.KeyboardEvent =>
    if (e.key == "Backspace") {
      setState(state.goBackUp)
    }else {
      val tileId : TileId = TileId.fromShortcut(e.key)
      state.nodesWithId.find(_._2 == tileId).map(_._1) match {
        case Some(_ : Category) => setState(state.drillDown(tileId))
        case Some(link: Link) => dom.window.location.replace(link.href)
        case _ => ()
      }
    }
  }

  override def initialState: CurrentTreePosition = CurrentTreePosition.initial

  def createTileComponent(node: Node, tileId: TileId) : ReactElement =
    TileComponent(node = node, tileId = tileId).withKey(tileId.shortcut)



  override def render(): ReactElement = {
    val nodes = state.nodesWithId.map{case (node, tileId) => createTileComponent(node, tileId)}
    html.div(html.className := "container-fluid bookmark-navigator-component mt-3 mb-3 h-100")(
      nodes.grouped(nodes.length / 2).toSeq.zipWithIndex.map { case (row, index) =>
        html.div(html.className := "row h-25", html.key := s"card-row-$index")(row: _*)
      }
    )
  }

}

