package models

import play.api.test.FakeApplication
import play.api.test.Helpers._
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import mediators.StringUtil
import scalax.io._

/**
 * Created by rgarbi on 1/8/14.
 */
class PhotoTest extends FunSuite with BeforeAndAfter {


  test("I can store a photo."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val photo: Photo = createPhoto()

      Photo.create(photo.uuid, photo.album_uuid, photo.image)

      val allPhotos: List[Photo] = Photo.all()
      assert(allPhotos.size === 1)
      Photo.delete(photo.uuid)

    }
  }

  test("I can store a photo using the other method."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val photo: Photo = createPhoto()
      Photo.create(photo)

      val allPhotos: List[Photo] = Photo.all()
      assert(allPhotos.size === 1)
      Photo.delete(photo.uuid)
    }
  }

  test("I can get a list of photos"){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {

      val photoCount: Integer = 100;

      for(i <- 1 to photoCount){
        val photo: Photo = createPhoto()
        Photo.create(photo)
      }

      val allPhotos: List[Photo] = Photo.all()
      assert(allPhotos.size === photoCount)

      allPhotos.foreach{ photo: Photo =>
        Photo.delete(photo.uuid)
      }

    }
  }

  test("I can get an photo by uuid."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val album: Album = new AlbumTest().createAlbum()

      val photo: Photo = new Photo(new StringUtil().uuidGenerator(), album.uuid, getFileBlob())
      Photo.create(photo)

      val savedPhotos: List[Photo] = Photo.getAllByAlbumUUID(album.uuid)
      assert(savedPhotos.size === 1)

      Photo.delete(photo.uuid)
    }
  }

  test("I get null if a photo is not found by uuid"){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {

      val savedPhotos: List[Photo] = Photo.getAllByAlbumUUID(new StringUtil().uuidGenerator())
      assert(savedPhotos.size === 0)
    }
  }


  def createPhoto(): Photo = {

    val uuid = new StringUtil().uuidGenerator()
    val albumUUID: String = new StringUtil().uuidGenerator()
    val photo: Array[Byte] = getFileBlob()

    return Photo(uuid, albumUUID, photo);

  }

  def getFileBlob(): Array[Byte] = {
    val byteArray: Array[Byte] = Resource.fromFile(this.getClass().getResource("/photo.jpg").getFile).byteArray
    return byteArray
  }


}
