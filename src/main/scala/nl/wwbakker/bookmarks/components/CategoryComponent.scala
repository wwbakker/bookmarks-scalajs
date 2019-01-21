package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.model.Category
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class CategoryComponent extends StatelessComponent {

  case class Props(category : Category)
  override def render(): ReactElement =
        html.li(s"${props.category.caption} (${props.category.shortcut})")

}

