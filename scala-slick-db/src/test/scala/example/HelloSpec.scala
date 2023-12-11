package example

import example.Entities.PlayerTable

import scala.concurrent.duration._
import slick.lifted.TableQuery
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FutureOutcome}
import slick.jdbc.H2Profile.api._

import scala.concurrent.{Await, Future}

class HelloSpec extends AsyncWordSpec
  with Matchers
  with ScalaFutures
  with BeforeAndAfterAll
  with BeforeAndAfterEach {

  private val playerTable = TableQuery[PlayerTable]
  val db = Connection.db
  createTable
  Thread.sleep(1000)

  "PlayerService" should {
    "insert a player to the table and filter by country" in {

      val player1 = Player(1L, "Disney", "USA", None)
      val player2 = Player(2L, "Disney", "Canada", None)
      val result = db.run(playerTable ++= Seq(player1, player2))
      Await.result(result, 1.seconds)
      result flatMap { insertStatus =>
        insertStatus shouldBe Some(2)
      }

      // get inserted record from database and check
      val query = playerTable.filter(_.name === "Disney")
      val playerResult: Future[Seq[Player]] = db.run(query.result)
      Await.result(playerResult, 2.seconds)
      playerResult map { playersFromDB =>
        playersFromDB should contain allElementsOf (Seq(player1, player2))
      }
    }
  }

  def createTable: Future[Int] = {
    val createQuery: DBIO[Int] =
      sqlu"""create table "Player"(
             "PlayerId" bigserial primary key,
             "Name" varchar not null,
             "Country" varchar not null,
             "Dob" date
            ) """
    db.run(createQuery)
  }
}
