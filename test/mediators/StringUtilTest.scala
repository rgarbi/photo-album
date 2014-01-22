package mediators

import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import java.lang.Long
import scala.util.Random
import scala.collection.mutable.HashMap

/**
 * Created by rgarbi on 1/8/14.
 */
class StringUtilTest extends FunSuite with BeforeAndAfter {


  test("I never get the same uuid twice"){
    val uuids: HashMap[String, String] = new HashMap[String, String]
    for(i <- 1 to 1000){
      uuids.put(new StringUtil().uuidGenerator(), new StringUtil().uuidGenerator())
    }

    assert(uuids.size === 1000)
  }

  test("Hello World"){

    for(i <- 1 to 1000){
      System.out.println( "Hello World")
    }

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
