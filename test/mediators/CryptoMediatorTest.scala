package mediators

import java.util.UUID
import play.api.test.FakeApplication
import play.api.test.Helpers._
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import scala.collection.mutable.HashMap
import java.lang.{IllegalArgumentException, Long}
import scala.util.Random
import org.jasypt.digest.StandardStringDigester

/**
 * Created by rgarbi on 1/8/14.
 */
class CryptoMediatorTest extends FunSuite with BeforeAndAfter {


  test("I can hash a password"){
    val password: String = "password"
    val hashed_password = new CryptoMediator().encryptPassword(password)

    assert(password.equals(hashed_password) === false)
  }

  test("I can check a hashed password and it matches"){
    val password: String = "password"
    val hashed_password = new CryptoMediator().encryptPassword(password)
    assert(password.equals(hashed_password) === false)

    val matches: Boolean = new CryptoMediator().passwordMatches(password, hashed_password)

    assert(true === matches)

  }

  test("I can check a hashed password and it does not matches"){
    val password: String = "password"
    val hashed_password = new CryptoMediator().encryptPassword(password)
    assert(password.equals(hashed_password) === false)

    val matches: Boolean = new CryptoMediator().passwordMatches("Some other string", hashed_password)

    assert(false === matches)

  }

  test("I can hash a really long password"){
    val password: String = stringGenerator()
    val hashed_password = new CryptoMediator().encryptPassword(password)
    assert(password.equals(hashed_password) === false)

    val matches: Boolean = new CryptoMediator().passwordMatches(password, hashed_password)

    assert(true === matches)
  }

  test("I cannot hash an empty string"){
    val password: String = ""
    try{
      new CryptoMediator().encryptPassword(password)
    }
    catch{
      case iae: IllegalArgumentException => {
        println("Exception!")
      }
    }
  }

  test("The encyptor is initialized"){
    assert(true === new CryptoMediator().buildDigest().isInitialized)
  }

  def stringGenerator(): String = {

    val length: Integer = 5000
    var salt: String = ""

    while(salt.length < length){
      salt += Long.toHexString(new Random(System.nanoTime()).nextLong())
    }

    return salt.substring(0,length)
  }

}
