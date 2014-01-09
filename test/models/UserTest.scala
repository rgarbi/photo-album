package models

import java.util.UUID
import play.api.test.FakeApplication
import play.api.test.Helpers._
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite

/**
 * Created by rgarbi on 1/8/14.
 */
class UserTest extends FunSuite with BeforeAndAfter {


  test("I can store a user."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val uuid = UUID.randomUUID().toString();
      val username: String = "User" + UUID.randomUUID().toString();
      val password: String = "Password" + UUID.randomUUID().toString();
      val salt: String = "Salt" + UUID.randomUUID().toString();
      User.create(uuid, username, password, salt)

      var allUsers: List[User] = User.all()
      assert(allUsers.size === 1)
      //TestProp.delete(uuid)

    }
  }


}
