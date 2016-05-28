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

  def makePagingBoardQuery(page: Int): String = {
    val maxLinkCount = 10

    val sb: StringBuilder = new StringBuilder()

    val pageNo = page - 1

    sb.append("SELECT * ")
    sb.append("FROM (SELECT @RNUM := @RNUM + 1 AS rnum, RESULT.* ")
    sb.append("FROM (SELECT no")
    sb.append(",writer")
    sb.append(",title")
    sb.append(",content")
    sb.append(",hidden_fl AS hiddenFl")
    sb.append(",reg_date AS regDate ")
    sb.append("FROM play.board ")
    sb.append("ORDER BY no DESC) RESULT, ")
    sb.append("(SELECT @RNUM := 0 ) CNT) A ")
    sb.append("WHERE 1=1 ")
    sb.append("AND " + pageNo + " * " + maxLinkCount + " < No AND No <= (" + pageNo + " + 1) * " + maxLinkCount)

    sb.toString
  }



  def index(page: Int) = Action {
    val conn = db.getConnection()

    val list = new ListBuffer[BoardModel]

    var count = ""

    try {
      val stmt = conn.createStatement

      println("execute Query..")

      //val boardQuery = "SELECT no, writer, title, content, hidden_fl AS hiddenFl, reg_date AS regDate FROM play.board ORDER BY no DESC;"

      val boardQuery = makePagingBoardQuery(page)

      println("boardQuery : " + boardQuery)

      val rs = stmt.executeQuery(boardQuery)

      println("rs : " + rs)

      while (rs.next()) {
        val boardModel: BoardModel = new BoardModel(rs.getString("no").toInt, rs.getString("writer"), rs.getString("title"), rs.getString("content"), rs.getString("hiddenFl"), rs.getString("regDate"))

        list += boardModel
      }

      val countQuery = "SELECT COUNT(*) AS count FROM play.board ORDER BY no DESC;"

      val stmt2 = conn.createStatement

      val rs2 = stmt2.executeQuery(countQuery)

      println("rs2 : " + rs2)

      while (rs2.next()) {
        count = rs2.getString("count")

        //println("count : " + rs2.getString(0))
      }
    } finally {
      conn.close()
    }

    val boardModelList: List[BoardModel] = list.toList

    for(i:BoardModel <- boardModelList) {
      println("list : " + i)
    }

    Ok(views.html.index.render(boardModelList, count.toInt, page))

    //Ok(views.html.index("플레이 프레임워크 기반의 게시판 예제입니다.")(list))
    //Ok(views.html.index(boardModelList))
  }
}
