package controllers

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class HomeController @Inject() extends Controller {

  def index = Action {
    Ok(views.html.index.apply())
  }

  def userStartPage = Action {
    Ok(views.html.userStartPage.apply())
  }
}
