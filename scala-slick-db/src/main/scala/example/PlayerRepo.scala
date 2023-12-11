package example

import scala.concurrent.{Await, Future}
import example.Entities.PlayerTable
import slick.lifted.TableQuery
import slick.jdbc.H2Profile.api._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

class PlayerRepo {
  private val playerTable = TableQuery[PlayerTable]
  private val db = Connection.db
  private val createTableFut = createTable

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

  def save(player:Player): Future[String] = {
    val result = db.run(playerTable += player)
    var valuer = ""
//    result.onComplete {
//      value =>
//        value.map {
//          r => valuer="Records inserted: "+r
//        }
//    }
    Future.successful(valuer)
  }

  def findByName(name: String): Future[Seq[Player]] = {
    val query = playerTable.filter(_.name == name)
    val result = db.run(query.result)
    //Await.result(result, 1.seconds)
    result.map { value => "Records found: "+value }
    result
  }

  def findAll(): Future[Seq[Player]] = {
    val query = playerTable.filter(row => true)
    val players = db.run(query.result)
    Await.result(players, 1.seconds)
    players.value match {
      case Some(Success(value)) => println(value.size)
      case None => println()
    }
    players
  }

   def display(result: Future[Seq[Player]]): Unit = {
     result.value match {
       case Some(Success(value)) =>
         if(value.isEmpty)
           println("No records")
         else
           for (row <- result; r <- row)
             println(r.id + ", " + r.name + ", " + r.country)
       case None => println(None)
     }
  }

   def update(id:Long, country:String): Future[String] = {
    val updateCountry = playerTable
      .filter(_.id === id)
      .map(_.country)
      .update(country)

    val updateResult: Future[Int] = db.run(updateCountry)
    Await.result(updateResult, 1.seconds)
     println("updated")
    Future.successful("Record updated")
  }

  def delete(id:Long): Future[String] = {
    val deleteRecord = playerTable
      .filter(_.id === id)
      .delete

    val deleteResult: Future[Int] = db.run(deleteRecord)
    Await.result(deleteResult, 1.seconds)
    println("deleted")
    Future.successful("Record deleted")
  }
}
