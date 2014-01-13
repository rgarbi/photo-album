package controllers

import play.api.test._
import play.api.test.Helpers._
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import play.api.libs.ws.{WS, Response}
import models.User
import mediators.StringUtil


class UsersControllerTest extends FunSuite with BeforeAndAfter {

  test("Fire a load of tests") {
    running(TestServer(3333, FakeApplication(additionalConfiguration = inMemoryDatabase())), HTMLUNIT) { browser =>

      User.create(new StringUtil().uuidGenerator(), "User1", "hash_hash_hash_hash")
      User.create(new StringUtil().uuidGenerator(), "User2", "hash_hash_hash_hash")

      val response: Future[Response] = WS.url("http://loaclhost:3333/users")
        .get()
/*
      val responseFuture =  response map { response =>
        response.status match {
          case 200 => Some(response.ahcResponse.getResponseBody)
          case _ => None
        }
      }

      val result = Await.result(responseFuture, 10 seconds)
      println(result) */
    }

  }

}
