package mediators

import java.util.UUID

/**
 * Created by rgarbi on 1/9/14.
 */
class StringUtil {

  def uuidGenerator(): String = {
    return UUID.randomUUID().toString
  }

}
