package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.model.{Category, Link}
import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html

@react class LinkComponent extends StatelessComponent {

  case class Props(link: Link)

  override def render(): ReactElement =
    html.li(
      html.a(html.href := props.link.href)(s"${props.link.caption} (${props.link.shortcut})")
    )

}

