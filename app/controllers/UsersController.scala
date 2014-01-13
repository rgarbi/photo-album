package controllers

import play.api._
import play.api.mvc._
import mediators.UserMediator

object UsersController extends Controller {

  def all = Action {
    Ok(new UserMediator().getAllUsersAsJson())
  }

  def addUser = Action(parse.json) { request =>
    (request.body \ "name").asOpt[String].map { name =>
      Ok("Hello " + name)
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }

}