package domains

/**
 * Created by Yunseong on 2016-05-14.
 */
class CommentModel(val replyNo: Int, val replyWriter: String, val comment: String, val regDate: String) {
  override def toString: String = {
    "replyNo : " + replyNo + ", replyWriter : " + replyWriter + ", comment : " + comment + ", regDate : " + regDate
  }
}
