package example

import org.mockito._
import scala.concurrent.Future
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec._

class PlayerMockSpec
  extends AsyncWordSpec
  with Matchers
    with MockitoSugar {

  val mockRepo = mock[PlayerRepo]

  "PlayerMock" should {
    "Find all mock test" in {

      val player1 = Player(100L, "Disney", "USA", None)
      val player2 = Player(200L, "Disney", "Canada", None)

      val ps: Seq[Player] = Seq(player1, player2)
      val expected: Future[Seq[Player]] = Future.successful(ps)
      when(mockRepo.findAll()).thenReturn(expected)
      val actual = mockRepo.findAll()

      assert(expected == actual)

    }

    "Find all mock wrong test" in {

      val player1 = Player(100L, "Disney", "USA", None)
      val player2 = Player(200L, "Disney", "Canada", None)

      val ps: Seq[Player] = Seq(player1, player2)
      when(mockRepo.findAll()).thenReturn(Future.successful(ps))
      val actual = mockRepo.findAll()

      val ps2: Seq[Player] = Seq(player1)
      assert(ps2 != actual)
    }
  }

}
