package example
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class MovieService {

  private val movie1 = Movie(1, "First", 100)
  private val movie2 = Movie(2, "Second", 120)
  private var movies: List[Movie] = List(movie1, movie2)

  def getAllMovies(): Future[List[Movie]] = {
    Future.successful(movies)
  }

  def saveMovie(movie: Movie): Future[Movie] = {
    movies = movies :+ movie
    Future.successful(movie)
  }

  def updateMovie(id:Int, movie: Movie): Future[Movie] = {
    val updated = movies.filterNot(_.id == id) :+ movie
    movies = updated
    Future.successful(movie)
  }

  def deleteMovie(id:Int): Future[String] = {
    val updated = movies.filterNot(_.id == id)
    movies = updated
    Future.successful("Success")
  }
}
