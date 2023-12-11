package example


import java.time.LocalDate

import slick.jdbc.H2Profile.api._

object Entities {
  class PlayerTable(tag: Tag) extends Table[Player](tag, None, "Player") {
    override def * = (id, name, country, dob) <> (Player.tupled, Player.unapply)

    val id: Rep[Long] = column[Long]("PlayerId", O.AutoInc, O.PrimaryKey)
    val name: Rep[String] = column[String]("Name")
    val country: Rep[String] = column[String]("Country")
    val dob: Rep[Option[LocalDate]] = column[Option[LocalDate]]("Dob")
  }
}
