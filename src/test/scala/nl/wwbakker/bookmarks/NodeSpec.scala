package nl.wwbakker.bookmarks

import nl.wwbakker.bookmarks.model.{Category, Link, Root, TileId}
import org.scalatest.{FlatSpec, Matchers}

class NodeSpec extends FlatSpec with Matchers {

  object Bookmarks {
    def example =
      Root(
        Seq(
          Category(
            caption = "Search Engines",
            nodes = Seq(
              Link(
                caption = "Google",
                href = "https://www.google.nl"
              ),
              Link(
                caption = "Bing",
                href = "https://www.bing.com"
              )
            ))
        )
      )

    def duckDuckGoLink : Link =
      Link(
        caption = "DuckDuckGo",
        href = "https://www.google.nl"
      )

    def exampleReplaced =
      Root(
        Seq(
          Category(
            caption = "Search Engines",
            nodes = Seq(
              duckDuckGoLink,
              Link(
                caption = "Bing",
                href = "https://www.bing.com"
              )
            ))
        )
      )
  }

  "atSpec" should "work correctly" in {
    Bookmarks.example.withNodeReplacedAt(List(TileId.list.head, TileId.list.head), Bookmarks.duckDuckGoLink) shouldBe Bookmarks.exampleReplaced
  }
}
