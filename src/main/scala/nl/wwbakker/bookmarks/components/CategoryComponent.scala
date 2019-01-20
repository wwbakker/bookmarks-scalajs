package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.model.{Category, Link, Node}
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class CategoryComponent extends StatelessComponent {

  case class Props(node : Node)
  override def render(): ReactElement =
    props.node match {
      case Category(caption, shortcut, children) =>
        html.li(s"${props.node.caption} (${props.node.shortcut})")
      case Link(caption, shortcut, href) =>
        html.li(
          html.a(html.href := href)(s"${props.node.caption} (${props.node.shortcut})")
        )
    }

}

