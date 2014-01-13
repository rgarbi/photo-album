package models

import play.api.test.FakeApplication
import play.api.test.Helpers._
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import mediators.{StringUtil, CryptoMediator}

/**
 * Created by rgarbi on 1/8/14.
 */
class AlbumTest extends FunSuite with BeforeAndAfter {


  test("I can store an album."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val album: Album = createAlbum()
      Album.create(album.uuid, album.name, album.description)

      val allAlbums: List[Album] = Album.all()
      assert(allAlbums.size === 1)
      Album.delete(album.uuid)

    }
  }

  test("I can store an album using the other method."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val album: Album = createAlbum()
      Album.create(album)

      val allAlbums: List[Album] = Album.all()
      assert(allAlbums.size === 1)
      Album.delete(album.uuid)

    }
  }

  test("I can get a list of albums"){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {

      val userCount: Integer = 100;

      for(i <- 1 to userCount){
        val album: Album = createAlbum()
        Album.create(album)
      }

      val allAlbums: List[Album] = Album.all()
      assert(allAlbums.size === userCount)

      allAlbums.foreach{ album: Album =>
        Album.delete(album.uuid)
      }

    }
  }

  test("I can get an album by uuid."){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {
      val album: Album = createAlbum()
      Album.create(album)

      val savedAlbum: Album = Album.getUUID(album.uuid)
      assert(album.uuid === savedAlbum.uuid)
      assert(album.name === savedAlbum.name)
      assert(album.description === savedAlbum.description)

      User.delete(album.uuid)
    }
  }

  test("I get null if an album is not found by uuid"){
    running(FakeApplication(additionalConfiguration = inMemoryDatabase("test"))) {

      val savedAlbum: Album = Album.getUUID(new StringUtil().uuidGenerator())
      assert(savedAlbum === null)
    }
  }


  def createAlbum(): Album = {

    val uuid = new StringUtil().uuidGenerator()
    val name: String = "name" + new StringUtil().uuidGenerator()
    val description: String = "Description" + new StringUtil().uuidGenerator()

    return Album(uuid, name, description);

  }


}
