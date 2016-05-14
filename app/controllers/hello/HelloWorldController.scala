package controllers.hello

import javax.inject.Singleton

import javax.inject._

import play.api.mvc._

/**
 * Created by Yunseong on 2016-05-01.
 */

@Singleton
class HelloWorldController @Inject() extends Controller {

  val userForm = play.api.data.Form(
    play.api.data.Forms.tuple(
      "id" -> play.api.data.Forms.nonEmptyText,
      "password" -> play.api.data.Forms.nonEmptyText
    )
  )

  def index = Action {


    Ok(views.html.helloworld.hello("hello, world!"))
  }


  def request = Action { implicit request =>

    val (id, password) = userForm.bindFromRequest.get

    println("id : " + id + ", password : " + password)

    Ok(views.html.helloworld.req(id)(password))
  }
}