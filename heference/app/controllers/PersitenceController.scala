//TODO PACKAGE PERSITENCE OR SQL 설정 후 이동시켜야함
package controllers

import play.api._
import play.api.mvc._
import play.api.db._
improt play.api.Play.current

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class PersistenceController @Inject() (db: Database) extends Controller {

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
