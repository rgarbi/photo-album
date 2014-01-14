package controllers

import play.api._
import play.api.mvc._
import mediators.UserMediator
import models.Album
import play.libs.Json

object AlbumsController extends Controller {

  def allAlbums = Action {
    Ok(Json.toJson(Album.all()).asText())
  }

  def getAlbum(id: String) = Action {
    Ok(Json.toJson(Album.getUUID(id)).asText())
  }

  def allPhotos(album_id: String) = Action {
    Ok("allPhotos")
  }

  def addAlbum = Action(parse.json) { request =>
    (request.body \ "*").asOpt[String].map { name =>
      Ok(name)
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }

}