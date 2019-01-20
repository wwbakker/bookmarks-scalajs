package nl.wwbakker.bookmarks.model

trait HasChildren {
  def children: Seq[Node]
}

sealed trait Node {
  def caption: String

  def shortcut: String
}

case class Link(
                 caption: String,
                 shortcut: String,
                 href: String,
               ) extends Node

case class Category(
                     caption: String,
                     shortcut: String,
                     children: Seq[Node],
                   ) extends Node with HasChildren

case class Root(
                 children: Seq[Node],
               ) extends HasChildren


object Bookmarks {
  def example =
    Root(children =
      Seq(
        Category(
          caption = "Search Engines",
          shortcut = "S",
          children = Seq(
            Link(
              caption = "Google",
              shortcut = "G",
              href = "https://www.google.nl"
            ),
            Link(
              caption = "Bing",
              shortcut = "B",
              href = "https://www.bing.com"
            )
          )
        )
      )
    )
}