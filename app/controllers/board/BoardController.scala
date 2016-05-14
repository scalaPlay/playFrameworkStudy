package controllers.board

import java.sql.{Connection, ResultSet}
import javax.inject.Singleton

import javax.inject._

import domains.{CommentModel, BoardModel}
import play.api.mvc._
import play.api.db._

import scala.collection.mutable.ListBuffer

/**
 * Created by Yunseong on 2016-05-01.
 */
@Singleton
class BoardController @Inject() (db: Database) extends Controller {

  var conn: Connection = null

  val boardWriteForm = play.api.data.Form(
    play.api.data.Forms.tuple(
      "writer"    -> play.api.data.Forms.nonEmptyText,
      "password"  -> play.api.data.Forms.nonEmptyText,
      "title"     -> play.api.data.Forms.nonEmptyText,
      "content"   -> play.api.data.Forms.nonEmptyText
    )
  )

  def write = Action {
    Ok(views.html.board.write())
  }

  def executeQuery(query: String): ResultSet = {
    if(conn == null || conn.isClosed) conn = db.getConnection()

    val stmt = conn.createStatement

    val rs: ResultSet = stmt.executeQuery(query)

    rs
  }

  def executeUpdate(query: String): Int = {
    if(conn == null || conn.isClosed) conn = db.getConnection()

    val stmt = conn.createStatement

    stmt.executeUpdate(query)
  }

  def detailView(no: Int) = Action { implicit request =>

    def getBoardDetail: BoardModel = {
      try {
        var tmpModel: BoardModel = null

        val detailBoardQuery = "SELECT no, writer, title, content, hidden_fl AS hiddenFl FROM play.board WHERE no=" + no + ";"

        println(detailBoardQuery)

        val boardResultModel = executeQuery(detailBoardQuery)

        while (boardResultModel.next()) {
          tmpModel = new BoardModel(boardResultModel.getString("no").toInt, boardResultModel.getString("writer"), boardResultModel.getString("title"), boardResultModel.getString("content"), boardResultModel.getString("hiddenFl"))
        }

        tmpModel
      } finally {
        if(conn != null) {
          conn.close()
        }
      }
    }

    def getCommentList: List[CommentModel] = {
      try {
        val commentQuery = "SELECT reply_no, reply_writer, comment, reg_date FROM play.reply where board_no=" + no + ";"

        val commentResultModel = executeQuery(commentQuery)

        var commentList = new ListBuffer[CommentModel]

        while(commentResultModel.next) {
          val commentModel = new CommentModel(commentResultModel.getString("reply_no").toInt, commentResultModel.getString("reply_writer"), commentResultModel.getString("comment"), commentResultModel.getString("reg_date"))

          commentList += commentModel
        }

        commentList.toList
      } finally {
        if(conn != null) {
          conn.close()
        }
      }
    }

    val boardModel: BoardModel = getBoardDetail
    val commentList: List[CommentModel] = getCommentList

    Ok(views.html.board.detail(boardModel)(commentList))
  }

  def writeContent = Action { implicit request =>
    val (writer, password, title, content) = boardWriteForm.bindFromRequest.get

    val query = "INSERT INTO play.board (writer, title, content, password) VALUES ('" + writer + "','" + title + "','" + content.trim + "','" + password + "');"

    val result = executeUpdate(query)

    if(result == 1) {
      println("글쓰기 성공")
    }

    Ok(views.html.board.content(writer)(title)(content))
  }
}
