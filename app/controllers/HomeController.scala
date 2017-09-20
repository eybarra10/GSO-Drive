package controllers

import javax.inject._
import play.api.mvc._
import services.Mysql
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class HomeController @Inject()(mysql: Mysql) extends Controller {

  def index = Action {
    Ok(views.html.index.apply())
  }

  def userStartProfile = Action {
    Ok(views.html.userStartProfile.apply())
  }

  def createProfilePost: Action[AnyContent] = Action.async { implicit request =>
    request.body.asFormUrlEncoded map { bodyMap =>
      val vorname = bodyMap("Vorname").head
      val nachname = bodyMap("Nachname").head
      val geschlecht = bodyMap("Geschlecht").head
      val schulform = bodyMap("Schulform").head
      val istFahrer = bodyMap("istFahrer").head
      val email = bodyMap("Email").head
      val altEmail = bodyMap("AltEmail").head
      val handy = bodyMap("Handy").head
      val query = "INSERT INTO Benutzer (Vorname, Nachname, Geschlecht, Schulform, istFahrer, Email, AltEmail, Handy) VALUES (?, ?, ?, ?, ?, ?, ?, ?);"
      val eventuallyResult = mysql.connectionPool.sendPreparedStatement(query, Seq(vorname, nachname, geschlecht, schulform, istFahrer, email, altEmail, handy))
      // val lastId = mysql.connectionPool.sendPreparedStatement("SELECT LAST_INSERT_ID();")
      eventuallyResult map { result =>
        if (result.rowsAffected == 1) {
          val benutzer = Benutzer(vorname, nachname, geschlecht, schulform, istFahrer.toInt, email, altEmail, handy)
          Ok(views.html.StartPage(benutzer))
        } else NotFound("")
      }
    } getOrElse {
      Future.successful(NotFound(""))
    }
  }
}

case class Benutzer(vorname: String, nachname: String, geschlecht: String, schulform: String, istFahrer: Integer, email: String, altEmail: String, handy: String)
