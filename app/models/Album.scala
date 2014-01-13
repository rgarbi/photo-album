package models

import anorm.SqlParser._
import anorm._
import play.api.db.DB
import anorm.~

/**
 * Created by rgarbi on 1/13/14.
 */
case class Album(uuid: String, name: String, description: String)


object Album {

  val album = {
    get[String]("UUID") ~
      get[String]("NAME") ~
      get[String]("DESCRIPTION") map {
      case uuid~name~description => Album(uuid, name, description)
    }
  }

  def getUUID(uuid: String): Album = DB.withConnection { implicit c =>
    SQL("select * from ALBUMS where UUID = {uuid}")
      .on('uuid -> uuid).as(album.singleOpt).getOrElse(null)
  }

  def all(): List[Album] = DB.withConnection { implicit c =>
    SQL("select * from ALBUMS").as(album *)
  }

  def create(album: Album){
    create(album.uuid, album.name, album.description)
  }

  def create(uuid: String, name: String, description: String){
    DB.withConnection { implicit c =>
      SQL("insert into ALBUMS (UUID, NAME, DESCRIPTION) values ({uuid},{name},{description})")
        .on(
          'uuid -> uuid,
          'name -> name,
          'description -> description).execute()
    }
  }

  def delete(uuid: String) {
    DB.withConnection { implicit c =>
      SQL("delete from ALBUMS where UUID = {uuid}").on(
        'uuid -> uuid
      ).execute()
    }
  }

}




