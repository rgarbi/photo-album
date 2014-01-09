package mediators

import play.api.test.FakeApplication
import play.api.test.Helpers._
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import models.User

/**
 * Created by rgarbi on 1/8/14.
 */
class UserMediatorTest extends FunSuite with BeforeAndAfter {


  test("I can store a new user"){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val user_name: String = "username"
      val plain_text_password: String= "SomePassword"

      val user: User = new UserMediator().storeNewUser(user_name, plain_text_password)
      assert(user != null)

      val allUsers: List[User] = User.all()
      assert(allUsers.size === 1)

      User.delete(user.user_name)
    }
  }

  test("I can store a new user and the password matches"){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val user_name: String = "username"
      val plain_text_password: String= "SomePassword"

      val user: User = new UserMediator().storeNewUser(user_name, plain_text_password)
      val allUsers: List[User] = User.all()
      assert(allUsers.size === 1)

      assert(new CryptoMediator().passwordMatches(plain_text_password, user.hashed_password) === true)

      User.delete(user.user_name)
    }
  }

  test("When my username does not exist I get false"){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val user_name: String = "username"
      val plain_text_password: String= "SomePassword"

      val user: User = new UserMediator().storeNewUser(user_name, plain_text_password)
      val allUsers: List[User] = User.all()
      assert(allUsers.size === 1)

      assert(new UserMediator().checkIfUsernameExists(new StringUtil().uuidGenerator()) === false)

      User.delete(user.user_name)
    }
  }

  test("When my username does exist I get true"){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val user_name: String = "username"
      val plain_text_password: String= "SomePassword"

      val user: User = new UserMediator().storeNewUser(user_name, plain_text_password)
      val allUsers: List[User] = User.all()
      assert(allUsers.size === 1)

      assert(new UserMediator().checkIfUsernameExists(user_name) === true)

      User.delete(user.user_name)
    }
  }
}
