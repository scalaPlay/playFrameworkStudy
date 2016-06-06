package controllers

import javax.inject._
import dao.UserDAO
import models.User
import play.api.mvc._

import scala.concurrent.Future

import play.api.libs.concurrent.Execution.Implicits.defaultContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(userDao : UserDAO) extends Controller {

  def index = Action.async(
    userDao.all().map(users => Ok(views.html.board.index("aaaa")(users)))
  )


//  def echo = Action { request =>
    //    Ok("Got request [" + request + "]")
//    userDao.all().map( case (users) => Ok(views.html.board.Application.index(users));
//  }

  def echo = Action.async(
    userDao.all().map(users => Ok(views.html.board.index("aaaa")(users)))
  )


}
