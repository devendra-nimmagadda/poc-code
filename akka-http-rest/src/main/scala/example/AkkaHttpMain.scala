package example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import scala.util._

import scala.concurrent.ExecutionContext.Implicits.global
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait MovieJsonProtocol extends DefaultJsonProtocol {
  implicit val movieFormatter: RootJsonFormat[Movie] = jsonFormat3(Movie)
}

object AkkaHttpMain
  extends App
    with SprayJsonSupport
    with MovieJsonProtocol {
  implicit val system = ActorSystem("LocalServer")

  val movieService = new MovieService()

    val route = path("movies" / "heartbeat") {
      get {
        complete("Success")
      }
    } ~ path("movies" / "all") {
      get {
        onComplete(movieService.getAllMovies()) {
          case Success(res) => complete(res)
          case Failure(ex) => complete(StatusCodes.InternalServerError)
        }
      }
    } ~ path("movies" / "save") {
      put {
      entity(as[Movie]) { movie =>
        onComplete(movieService.saveMovie(movie)) {
          case Success(res) => complete(res)
          case Failure(ex) => complete(StatusCodes.InternalServerError)
        }
      }
    }
   } ~ path("movies" / "update" / IntNumber) { id =>
      post {
        entity(as[Movie]) { movie =>
          onComplete(movieService.updateMovie(id, movie)) {
            case Success(res) => complete(res)
            case Failure(ex) => complete(StatusCodes.InternalServerError)
          }
        }
      }
    } ~ path("movies" / "delete" / IntNumber) { id =>
      delete {
        onComplete(movieService.deleteMovie(id)) {
          case Success(res) => complete(res)
          case Failure(ex) => complete(StatusCodes.InternalServerError)
        }
      }
    }


  val server = Http().newServerAt("localhost", 9090).bind(route)
  server.map { _ =>
    println("Successfully started on localhost:9090 ")
  } recover { case ex =>
    println("Failed to start the server due to: " + ex.getMessage)
  }

}
