package nl.wwbakker.bookmarks.model

sealed trait Node {
  def caption : String
  def className : String
}

case class Category(caption: String,
                    nodes: Seq[Node],
                   ) extends Node {
  def className = "category border-warning"
}

case class Link(caption: String,
                href: String,
               ) extends Node {
  def className = "link border-primary"
}

case object Empty extends Node {
  def caption = ""
  def className = "empty"
}

case class Root(categories: Seq[Category])

object Bookmarks {
  def example =
    Root(categories =
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
}