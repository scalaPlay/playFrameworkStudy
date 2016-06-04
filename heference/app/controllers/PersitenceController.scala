//TODO PACKAGE PERSITENCE OR SQL 설정 후 이동시켜야함
package controllers

import javax.inject.Inject

import play.api.Play.current
import play.api.mvc._
import play.api.db._

/**
	* This controller creates an `Action` to handle HTTP requests to the
	* application's home page.
	*/

@Singleton
class PersistenceController @Inject() (db: Database) extends Controller {
	def index = Action {
		var outString = "Number is "
		val conn = db.getConnection()

		try {
			val stmt = conn.createStatement
			val rs = stmt.executeQuery("SELECT 9 as testkey ")

			while (rs.next()) {
				outString += rs.getString("testkey")
			}
		} finally {
			conn.close()
		}
		Ok(outString)
	}

}


  def index = Action {
   // Ok(views.html.index("Your new application is ready."))
   var outString = "BOARD .... "
   val conn = db.getConnection()

   try {
	   val stmt = conn.createStatement
	   val rs = stmt.executeQuery("SELECT no, title, content FROM BOARD")

	   while (rs.next()) {
		   outString += rs.getString("???")
	   } finally {
		   conn.close()
	   }

	   OK(outString)
  }

}
