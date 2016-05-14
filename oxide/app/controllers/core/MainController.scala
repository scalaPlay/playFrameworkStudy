package controllers.core

import javax.inject.Singleton

import javax.inject._

import domains.BoardModel
import play.api.db._
import play.api.mvc._

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/**
 * Created by Yunseong on 2016-05-01.
 */
@Singleton
class MainController @Inject() (db: Database) extends Controller {
  def index = Action {



    val conn = db.getConnection()

    val list = new ListBuffer[BoardModel]

    try {
      val stmt = conn.createStatement

      println("execute Query..")

      val query = "select no, writer, title, content, hidden_fl AS hiddenFl from play.board;"

      println("query : " + query)

      val rs = stmt.executeQuery(query)

      println("rs : " + rs)



      while (rs.next()) {
        val boardModel: BoardModel = new BoardModel(rs.getString("no").toInt, rs.getString("writer"), rs.getString("title"), rs.getString("content"), rs.getString("hiddenFl"))

        list += boardModel
      }
    } finally {
      conn.close()
    }

    val boardModelList: List[BoardModel] = list.toList



    for(i:BoardModel <- boardModelList) {
      println("list : " + i)
    }

    Ok(views.html.index.render(boardModelList))

    //Ok(views.html.index("플레이 프레임워크 기반의 게시판 예제입니다.")(list))
    //Ok(views.html.index(boardModelList))
  }
}
