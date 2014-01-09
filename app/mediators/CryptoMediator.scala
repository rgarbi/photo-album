package mediators

import scala.util.Random
import java.lang.Long

/**
 * Created by rgarbi on 1/9/14.
 */
class CryptoMediator {


  def saltGenerator(): String = {
    val length: Integer = 50
    var salt: String = ""

    while(salt.length < length){
      salt += Long.toHexString(new Random(System.nanoTime()).nextLong())
    }

    return salt.substring(0,length)
  }

}
