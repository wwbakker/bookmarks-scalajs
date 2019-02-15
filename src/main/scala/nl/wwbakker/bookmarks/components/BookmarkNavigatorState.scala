package nl.wwbakker.bookmarks.components

import nl.wwbakker.bookmarks.data.SerializerDeserializer
import nl.wwbakker.bookmarks.model._

case class BookmarkNavigatorState(root : Root,
                                  tileIdPath : List[TileId])

object BookmarkNavigatorState {

  def initial: BookmarkNavigatorState = {
    SerializerDeserializer.default match {
      case Left(error) => println("decode from json error", error)
      case Right(result) => println("decode from json success", result)
    }
    BookmarkNavigatorState(
      SerializerDeserializer.default.toOption.get,
      Nil
    )
  }
}