package nl.wwbakker.bookmarks.model

trait CreateKey {
  def shortcut : String
  def key(currentPath : List[Category]) : String =
    currentPath.map(_.shortcut).mkString + shortcut
}

case class Link(caption: String,
                shortcut: String,
                href: String,
               ) extends CreateKey

case class Category(caption: String,
                    shortcut: String,
                    links: Option[Seq[Link]],
                    subCategories: Option[Seq[Category]],
                   ) extends CreateKey

case class Root(categories: Seq[Category])

object Bookmarks {
  def example =
    Root(categories =
      Seq(
        Category(
          caption = "Search Engines",
          shortcut = "S",
          links = Some(Seq(
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
          )),
          subCategories = None
        )
      )
    )
}