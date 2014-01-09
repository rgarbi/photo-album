package mediators

import java.util.UUID
import play.api.test.FakeApplication
import play.api.test.Helpers._
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import scala.collection.mutable.HashMap

/**
 * Created by rgarbi on 1/8/14.
 */
class CryptoMediatorTest extends FunSuite with BeforeAndAfter {


  test("If I ask for 1000 salts none of them will be the same"){

    var saltStore: HashMap[String, String] = new HashMap[String, String];

    for(i <- 1 to 1000){
      val salt: String = new CryptoMediator().saltGenerator()
      saltStore.put(salt, salt)
    }

    assert(saltStore.size === 1000)

  }

  test("If I ask for a salt I will get a string of 50 characters."){

    val salt: String = new CryptoMediator().saltGenerator()

    assert(salt.length === 50)
  }




}
