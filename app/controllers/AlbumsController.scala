package controllers

import play.api._
import play.api.mvc._
import mediators.UserMediator

object AlbumsController extends Controller {

  def allAlbums = Action {
    Ok("allAlbums")
  }

  def getAlbum(id: String) = Action {
    Ok("getAlbum")
  }

  def allPhotos = Action {
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