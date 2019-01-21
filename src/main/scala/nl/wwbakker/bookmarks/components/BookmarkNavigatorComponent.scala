package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.data.SerializerDeserializer
import nl.wwbakker.bookmarks.model._
import slinky.core.Component
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.ul
import org.scalajs.dom

@react class BookmarkNavigatorComponent extends Component {
  type Props = Unit
  type State = CurrentTreePosition

  dom.document.onkeydown = { e : dom.KeyboardEvent =>
    if (e.key == "Backspace") {
      setState(state.goBackUp)
    }else {
      state.categoryWithShortcut(e.key)
        .foreach(category => setState(state.drillDown(category)))

      state.linkWithShortcut(e.key)
        .map(_.href)
        .foreach(dom.window.location.replace)

    }
  }

  override def initialState: CurrentTreePosition = CurrentTreePosition.initial

  def createCategoryComponent(category: Category) : ReactElement =
    CategoryComponent(category = category).withKey(category.key(state.currentPath))

  def createLinkComponent(link: Link) : ReactElement =
    LinkComponent(link = link).withKey(link.key(state.currentPath))

  override def render(): ReactElement = ul(
    state.categories.map(createCategoryComponent) ++
      state.links.map(createLinkComponent)
  )
}


case class CurrentTreePosition(root : Root,
                               currentPath : List[Category]) {

  def categoryWithShortcut(shortcut : String) : Option[Category] =
    categories.find(_.shortcut.toUpperCase == shortcut.toUpperCase)

  def linkWithShortcut(shortcut : String) : Option[Link] =
    links.find(_.shortcut.toUpperCase == shortcut.toUpperCase)

  def categories : Seq[Category] =
    currentPath match {
      case Nil =>
        root.categories
      case mostSpecificCategory :: _ =>
        mostSpecificCategory.subCategories.getOrElse(Nil)
    }

  def links : Seq[Link] =
    currentPath match {
      case Nil => Nil
      case mostSpecificCategory :: _ =>
        mostSpecificCategory.links.getOrElse(Nil)
    }

  def drillDown(toCategory : Category) : CurrentTreePosition =
    CurrentTreePosition(root, toCategory :: currentPath)

  def goBackUp : CurrentTreePosition =
    CurrentTreePosition(root, currentPath.drop(1))
}

object CurrentTreePosition {
  def initial = {
    SerializerDeserializer.default match {
      case Left(error) => println("decode from json error", error)
      case Right(result) => println("decode from json success", result)
    }
    CurrentTreePosition(
      SerializerDeserializer.default.toOption.get,
      Nil
    )
  }
}