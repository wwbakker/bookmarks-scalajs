package nl.wwbakker.bookmarks.data
import io.circe._
import io.circe.parser._
import io.circe.syntax._
import io.circe.generic.auto._
import nl.wwbakker.bookmarks.model._

object SerializerDeserializer {

  val file4 : String = Bookmarks.example.asJson.noSpaces

  def default: Either[Error, Root] = decode[Root](file4)
}
