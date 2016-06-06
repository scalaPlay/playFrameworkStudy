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


  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    var outString = "Number is "
//    val conn = db.getConnection()
//
//    try {
//      val stmt = conn.createStatement()
//      val rs = stmt.executeQuery("SELECT 9 as testkey")
//
//      while (rs.next()) {
//        outString += rs.getString("testKey")
//      }
//    } finally {
//      conn.close()
//    }

    Ok(outString)
  }

//  def echo = Action { request =>
    //    Ok("Got request [" + request + "]")
//    userDao.all().map( case (users) => Ok(views.html.board.Application.index(users));
//  }

  def echo = Action.async(
    userDao.all().map(users => Ok(views.html.board.index("aaaa")(users)))
  )


}
