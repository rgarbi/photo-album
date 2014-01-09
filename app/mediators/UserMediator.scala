package mediators

import models.User

/**
 * Created by rgarbi on 1/9/14.
 */
class UserMediator {

  def checkIfUsernameExists(user_name: String): Boolean = {
   if(User.getByUserName(user_name) == null){
      return false
   }

    return true
  }

  def storeNewUser(user_name: String, plain_text_password: String): User = {
    val uuid: String = new StringUtil().uuidGenerator()
    val hashed_password: String = new CryptoMediator().encryptPassword(plain_text_password)

    val user: User = new User(uuid, user_name, hashed_password)
    User.create(user)

    return user
  }



}
