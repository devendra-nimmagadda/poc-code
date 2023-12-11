package example

import scala.util._
import scala.concurrent.ExecutionContext.Implicits.global

class PlayerService {
  private val repo = new PlayerRepo()
  Thread.sleep(1000)

   def insert(id:Long, name:String, country:String): Unit = {

     val player1 = Player(id, name, country, None)
     var result = repo.save(player1)
//     result map {
//       case Success(value) => println(value)
//       case Failure(exception) => exception.printStackTrace()
//     }
  }

   def showAll(): Unit = {
     var result = repo.findAll()
     repo.display(result)
  }

  def update() : Unit = {
    repo.update(1L, "Germany")
  }

  def delete(): Unit = {
    repo.delete(1L)
  }

}
