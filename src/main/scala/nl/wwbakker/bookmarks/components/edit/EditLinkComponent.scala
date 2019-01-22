package nl.wwbakker.bookmarks.components.edit

import nl.wwbakker.bookmarks.model.Node
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class EditLinkComponent extends Component {

  case class State()
  case class Props(node : Node, shortcut : String)
  override def render(): ReactElement =
    ???

  override def initialState: State = ???
}

