package dao

import javax.inject.Inject

import models.User
import play.api.db.slick.{HasDatabaseConfigProvider, DatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future

/**
  * Created by kohunmin on 2016. 5. 28..
  */
class UserDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile]{
  import driver.api._

  private val Users = TableQuery[UserTable]

  def all(): Future[Seq[User]] = db.run(Users.result)

  // _ 는 모두를 의미
  def insert(user: User): Future[Unit] =
    db.run(Users += user).map(_ => ())

  private class UserTable(tag: Tag) extends Table[User](tag,"User") {

    def id = column[Int]("ID", O.PrimaryKey)
    def email = column[String]("EMAIL")
    def password = column[String]("PASSWORD")
    def fullname = column[String]("FULLNAME")
    def isAdmin = column[Boolean]("ISADMIN")

    override def * = (id,email,password,fullname,isAdmin) <> (User.tupled , User.unapply _)
  }

}
