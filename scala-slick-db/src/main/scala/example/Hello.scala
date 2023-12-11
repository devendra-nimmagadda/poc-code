package example


object Hello extends App {
  private val playerService = new PlayerService()

  playerService.showAll()

  playerService.insert(1L, "Disney", "USA")
  playerService.insert(2L, "Disney", "Canada")

  playerService.showAll()
  playerService.update()

  playerService.showAll()
  playerService.delete()

  playerService.showAll()
}

trait Greeting {
  lazy val greeting: String = "hello"
}
