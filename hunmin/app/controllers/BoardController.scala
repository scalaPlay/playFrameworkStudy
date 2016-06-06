package controllers

import javax.inject._

import play.api.db.Database
import play.api.mvc.{Controller, Action}


/**
  * Created by kohunmin on 2016. 4. 23..
  */

@Singleton
class BoardController @Inject()(db: Database) extends Controller{

  def index = Action {
    var outString = "Number is "
    val conn = db.getConnection()

    try {
      val stmt = conn.createStatement()
      val rs = stmt.executeQuery("SELECT * from User")

      while (rs.next()) {
        outString += rs.getString("name")
      }
    } finally {
      conn.close()
    }

    Ok(outString)
  }


  def join = Action {
    Ok("hello")
  }
}
