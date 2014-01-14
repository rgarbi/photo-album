package models

import java.sql.Blob
import anorm.SqlParser._
import anorm._
import play.api.db.DB
import play.api.Play.current
import scala.Array
import org.h2.jdbc.JdbcBlob

/**
 * Created by rgarbi on 1/13/14.
 */
case class Photo(uuid: String, album_uuid: String, image: Array[Byte])

object Photo {

  val photo = {
    get[String]("UUID") ~
      get[String]("ALBUM_UUID") ~
      get[Array[Byte]]("IMAGE")(implicitly[Column[Array[Byte]]]) map {
      case uuid~album_uuid~image => Photo(uuid, album_uuid, image)
    }
  }

  def bytes(columnName: String): RowParser[Array[Byte]] = get[Array[Byte]](columnName)(implicitly[Column[Array[Byte]]])

  implicit def rowToByteArray: Column[Array[Byte]] = Column.nonNull {
    (value, meta) =>
      val MetaDataItem(qualified, nullable, clazz) = meta
      value match {
        case bytes: Array[Byte] => Right(bytes)
        case blob: JdbcBlob => Right(blob.getBytes(1, blob.length.toInt))
        case _ => Left(TypeDoesNotMatch("Cannot convert " + value + ":" + value.asInstanceOf[AnyRef].getClass + " to Array[Byte] for column " + qualified))
      }
  }

  def getAllByAlbumUUID(album_uuid: String): List[Photo] = DB.withConnection { implicit c =>
    SQL("select * from PHOTOS where ALBUM_UUID = {album_uuid}")
      .on('album_uuid -> album_uuid).as(photo *)
  }

  def all(): List[Photo] = DB.withConnection { implicit c =>
    SQL("select * from PHOTOS").as(photo *)
  }

  def create(photo: Photo){
    create(photo.uuid, photo.album_uuid, photo.image)
  }

  def create(uuid: String, album_uuid: String, image: Array[Byte]){
    DB.withConnection { implicit c =>
      SQL("insert into PHOTOS (UUID, ALBUM_UUID, IMAGE) values ({uuid},{album_uuid},{image})")
        .on(
          'uuid -> uuid,
          'album_uuid -> album_uuid,
          'image -> image).execute()
    }
  }

  def deleteByAlbumUUID()(album_uuid: String) {
    DB.withConnection { implicit c =>
      SQL("delete from PHOTOS where ALBUM_UUID = {album_uuid}").on(
        'album_uuid -> album_uuid
      ).execute()
    }
  }

  def delete(uuid: String) {
    DB.withConnection { implicit c =>
      SQL("delete from PHOTOS where UUID = {uuid}").on(
        'uuid -> uuid
      ).execute()
    }
  }

}
