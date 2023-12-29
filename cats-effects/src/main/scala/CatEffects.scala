import cats.effect.IO
import cats.instances.option._
import cats.Functor


object CatEffects extends App {

    // Create a functor for mapping Option values and use it in an example.
    trait Functor[T[_]] {
      def map[A, B](v: T[A])(f: A => B): T[B]
    }

    implicit val optionFunctor: Functor[Option] = new Functor[Option] {
      override def map[A, B](v: Option[A])(f: A => B) = v map f
    }
    val result = Functor[Option].map(Option(10, 20))(_.swap)
    println(result)

  // Create a method that do some side effect along with returning a random integer.
    def getValue(x:Int) : Int = {
      var value = x * 2
      println(value)
      scala.util.Random.nextInt(100)
    }
    println(getValue(10))

    val ioa = IO {
      getValue(110)
    }

    val program: IO[Int] =
      for {
        a <- ioa
        b <- ioa
      } yield a + b
    println(program)
    program.unsafeRunSync()
}
