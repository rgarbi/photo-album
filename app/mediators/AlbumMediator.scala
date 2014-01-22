package mediators

import models.Photo

/**
 * Created by Richard on 1/20/14.
 */
class AlbumMediator {

  def getAllPhotosForAnAlbum(id: String): List[Photo] = {
      return Photo.getAllByAlbumUUID(id)
  }






}
