package domains

/**
 * Created by Yunseong on 2016-05-06.
 */
class BoardModel(val no: Int, val writer: String, val title: String, val content: String, val hiddenFl: String) {
  override def toString(): String = {
    "no : " + no + ", writer : " + writer + ", title : " + title + ", content : " + content
  }
}