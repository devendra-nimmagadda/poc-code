import AkkaActorsMain.Check
import akka.actor.{Actor, ActorLogging, ActorSystem, PoisonPill, Props}
import akka.util.Timeout
import akka.pattern.ask

import java.util.concurrent.TimeUnit
import scala.concurrent.{Await, Future}

case class Message(msg:String)

object AkkaActorsMain extends App {
  val system = ActorSystem("akka-actors")

  val msg = Message("Welcome")

  // parent actor
  val parent = system.actorOf(Props[ParentActor], "parent-actor")
  parent ! msg

  object StopChild
  object Stop

  object CheckChild
  object Check

  parent ! CheckChild
  //parent ! StopChild
  parent ! CheckChild

  implicit val timeout = Timeout(30, TimeUnit.SECONDS)
  implicit val executionContext = system.dispatchers.lookup("fixed-thread-pool")
  val requests = (1 to 10).map(i=> Message("Hello ..."+i))
  val futures = requests.map {
    msg =>
      val simpleActor = system
        .actorOf(Props[SimpleActor]
                .withDispatcher("fixed-thread-pool")
        )
      (simpleActor ? msg).mapTo[Message]
  }

  val results = Await.result(Future.sequence(futures), timeout.duration)

  results.foreach(x => println(x.msg))

  parent ! PoisonPill
  system.terminate()

  class ParentActor extends Actor with ActorLogging {

    val child = context.actorOf(Props[ChildActor], "child-actor")

    override def receive: Receive = {
      case msg: Message => {
        log.info("parent received message")
        child ! msg
      }
      case CheckChild => child ! Check
      case StopChild => child ! Stop
    }
  }

  class ChildActor extends Actor with ActorLogging {

    override def preStart(): Unit = {
      //println("child pre-start called...")
      log.info("child pre-start called...")
    }

    override def postStop(): Unit = {
      //println("child pre-stop called...")
      log.info("child pre-stop called...")
    }

    override def receive: Receive = {
      case m: Message => {
        //println("child received message... "+m.msg)
        log.info(s"child actor received message: " + m.msg)
      }
      case Check => log.info("checking child")
      case Stop => {
        log.info("Killing child")
        throw new RuntimeException("stopped")
      }
    }
  }

  class SimpleActor extends Actor  {
    override def receive: Receive = {
      case m: Message => println("Message received ... " + m.msg)
    }
  }

}


