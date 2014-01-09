package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current


/**
 * Created by rgarbi on 1/8/14.
 */
case class User(uuid: String, user_name: String, hashed_password: String)



object User {
  val table_name: String = "USERS";

  val user = {
      get[String]("UUID") ~
      get[String]("USER_NAME") ~
      get[String]("HASHED_PASSWORD") map {
      case uuid~user_name~hashed_password => User(uuid, user_name, hashed_password)
    }
  }

  def getByUserName(user_name: String): User = DB.withConnection { implicit c =>
    SQL("select * from USERS where USER_NAME = {user_name}")
      .on('user_name -> user_name).as(user.singleOpt).getOrElse(null)
  }

  def all(): List[User] = DB.withConnection { implicit c =>
    SQL("select * from USERS").as(user *)
  }

  def create(user: User){
    create(user.uuid, user.user_name, user.hashed_password)
  }

  def create(uuid: String, user_name: String, hashed_password: String){
    DB.withConnection { implicit c =>
      SQL("insert into USERS (UUID, USER_NAME, HASHED_PASSWORD) values ({uuid},{user_name},{hashed_password})")
        .on(
          'uuid -> uuid,
          'user_name -> user_name,
          'hashed_password -> hashed_password).execute()
    }
  }

  def delete(user_name: String) {
    DB.withConnection { implicit c =>
      SQL("delete from USERS where USER_NAME = {user_name}").on(
        'user_name -> user_name
      ).execute()
    }
  }

}
