package mediators

import scala.util.Random
import java.lang.Long
import org.jasypt.util.password.ConfigurablePasswordEncryptor
import org.jasypt.digest.config.SimpleDigesterConfig
import org.jasypt.digest.StandardStringDigester
import com.lambdaworks.crypto.SCryptUtil

/**
 * Created by rgarbi on 1/9/14.
 */
class CryptoMediator {


  def encryptPassword(plain_text_password: String): String = {
    //val passwordEncryptor: StandardStringDigester = buildDigest()
    return SCryptUtil.scrypt(plain_text_password, 16384, 9, 9)
  }

  def passwordMatches(plain_text_password: String, encrypted_password: String): Boolean = {
    //val passwordEncryptor: StandardStringDigester = buildDigest()
    return SCryptUtil.check(plain_text_password, encrypted_password)
  }

  def buildDigest(): StandardStringDigester = {
    val stringEncryptor: StandardStringDigester = new StandardStringDigester();
    stringEncryptor.setAlgorithm("SHA-512")
    stringEncryptor.setIterations(50000)
    stringEncryptor.setSaltSizeBytes(100)
    stringEncryptor.initialize()
    return stringEncryptor
  }
}
