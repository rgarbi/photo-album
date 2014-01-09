package models

import java.util.UUID
import play.api.test.FakeApplication
import play.api.test.Helpers._
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import mediators.{StringUtil, CryptoMediator}

/**
 * Created by rgarbi on 1/8/14.
 */
class UserTest extends FunSuite with BeforeAndAfter {


  test("I can store a user."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val aUser: User = createUser()
      User.create(aUser.uuid, aUser.user_name, aUser.hashed_password)

      val allUsers: List[User] = User.all()
      assert(allUsers.size === 1)
      User.delete(aUser.user_name)

    }
  }

  test("I can store a user using the other method."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val aUser: User = createUser()
      User.create(aUser)

      val allUsers: List[User] = User.all()
      assert(allUsers.size === 1)
      User.delete(aUser.user_name)

    }
  }

  test("I can get a list of users"){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {

      val userCount: Integer = 100;

      for(i <- 1 to userCount){
        val aUser: User = createUser()
        User.create(aUser)
      }

      val allUsers: List[User] = User.all()
      assert(allUsers.size === userCount)

      allUsers.foreach{ user: User =>
        User.delete(user.user_name)
      }

    }
  }

  test("I can get a user by user name."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val aUser: User = createUser()
      User.create(aUser)

      val savedUser: User = User.getByUserName(aUser.user_name)
      assert(aUser.uuid === savedUser.uuid)
      assert(aUser.user_name === savedUser.user_name)
      assert(aUser.hashed_password === savedUser.hashed_password)

      User.delete(aUser.user_name)
    }
  }

  test("I get null if a user is not found by username"){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {

      val savedUser: User = User.getByUserName(new StringUtil().uuidGenerator())
      assert(savedUser === null)
    }
  }

  test("I can store and then check the password of a user."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val aUser: User = createUser()
      val plain_test_password: String = aUser.hashed_password
      User.create(aUser.uuid, aUser.user_name, new CryptoMediator().encryptPassword(plain_test_password))

      val savedUser: User = User.getByUserName(aUser.user_name)
      assert(aUser.uuid === savedUser.uuid)
      assert(aUser.user_name === savedUser.user_name)
      assert(new CryptoMediator().passwordMatches(plain_test_password, savedUser.hashed_password) === true)

      User.delete(aUser.user_name)
    }
  }


  def createUser(): User = {

    val uuid = new StringUtil().uuidGenerator()
    val username: String = "User" + new StringUtil().uuidGenerator()
    val password: String = "Password" + new StringUtil().uuidGenerator()

    return User(uuid, username, password);

  }


}
