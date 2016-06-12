package controllers

import javax.inject._

import dao.UserDAO
import play.api.mvc.{Controller, Action}
import play.api.libs.concurrent.Execution.Implicits.defaultContext


/**
  * Created by kohunmin on 2016. 4. 23..
  */

@Singleton
class BoardController @Inject()(userDao : UserDAO) extends Controller{

  def index = Action.async(
    userDao.all().map(users => Ok(views.html.board.index("aaaa")(users)))
  )

  def detail(id: Int) = Action.async {
    val user = userDao.detail(id)
    user.map { user =>
      user match {
        case Some(c) => Ok(views.html.board.detail(c))
        case None    => NotFound
      }
    }
  }

  def join = Action {
    Ok("hello")
  }
}
