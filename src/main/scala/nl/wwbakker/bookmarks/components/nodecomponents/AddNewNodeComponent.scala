package nl.wwbakker.bookmarks.components.nodecomponents

import nl.wwbakker.bookmarks.components.BookmarkNavigatorState
import nl.wwbakker.bookmarks.components.nodecomponents._
import nl.wwbakker.bookmarks.model.{Category, Link, Root, TileId}
import org.scalajs.dom
import org.scalajs.dom.raw.KeyboardEvent
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class AddNewNodeComponent extends Component {
  case class Props(tilePath: List[TileId])
  case class State()

  override def initialState: State = State()

  override def render(): ReactElement =
    html.div(
      html.className := "container-fluid create-tile-options mt-3 mb-3 h-100",
    )(
      html.h1("Add new node"),
      html.div(html.className := s"tile m-1 create-tile-option col")(
        html.div(html.className := "card-body")(
          html.span(html.className := "shortcut card-text")("C"),
          html.h5(html.className := "caption card-subtitle")("Category"),
        ),
        html.div(html.className := "card-body")(
          html.span(html.className := "shortcut card-text")("L"),
          html.h5(html.className := "caption card-subtitle")("Link"),
        ),
      )
    )
}

object AddNewNodeComponent {
  def create(tilePath: List[TileId]) : ReactElement =
    AddNewNodeComponent(tilePath)

  def keyboardDownStateChange(initialState : BookmarkNavigatorState, e : KeyboardEvent) : BookmarkNavigatorState =
    if (e.key == "Backspace")
      initialState.copy(tileIdPath = initialState.tileIdPath.dropRight(1))
    else if (e.key.toUpperCase == "C")
      initialState.copy(root = initialState.root.withNodeReplacedAt(tileIdPath = initialState.tileIdPath, Category("Unnamed category", Nil)))
    else if (e.key.toUpperCase == "L")
      initialState.copy(root = initialState.root.withNodeReplacedAt(tileIdPath = initialState.tileIdPath, Link("Unnamed link", "https://")))
    else
      initialState
}