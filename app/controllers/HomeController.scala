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

  def loginPost(email: String): Future[Option[Benutzer]] = {
    mysql.connectionPool.sendPreparedStatement("SELECT * FROM Benutzer WHERE Benutzer.Email = ?;", Seq(email)) map { result =>
      result.rows.flatMap(_.headOption) map { row =>
        val vorname = row("Vorname").asInstanceOf[String]
        val nachname = row("Nachname").asInstanceOf[String]
        val geschlecht = row("Geschlecht").asInstanceOf[String]
        val schulform = row("Schulform").asInstanceOf[String]
        val istFahrer = row("istFahrer").asInstanceOf[Integer]
        val email = row("Email").asInstanceOf[String]
        val altEmail = row("AltEmail").asInstanceOf[String]
        val handy = row("Handy").asInstanceOf[String]
        val passwort = row("Passwort").asInstanceOf[String]

        Benutzer(vorname, nachname, geschlecht, schulform, istFahrer, email, altEmail, handy, passwort)
      }
    }
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
      val passwort = bodyMap("Passwort").head
      val query = "INSERT INTO Benutzer (Vorname, Nachname, Geschlecht, Schulform, istFahrer, Email, AltEmail, Handy, Passwort) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
      val eventuallyResult = mysql.connectionPool.sendPreparedStatement(query, Seq(vorname, nachname, geschlecht, schulform, istFahrer, email, altEmail, handy, passwort))

      eventuallyResult map { result =>
        if (!loginPost(email).isCompleted) {
          if (result.rowsAffected == 1) {
            val benutzer = Benutzer(vorname, nachname, geschlecht, schulform, istFahrer.toInt, email, altEmail, handy, passwort)
            Ok(views.html.StartPage(benutzer))
          } else NotFound("")
        } else {
          val benutzer = Benutzer(vorname, nachname, geschlecht, schulform, istFahrer.toInt, email, altEmail, handy, passwort)
          Ok(views.html.StartPage(benutzer))
        }
      }
    } getOrElse {
      Future.successful(NotFound(""))
    }
  }
}

case class Benutzer(vorname: String, nachname: String, geschlecht: String, schulform: String, istFahrer: Integer, email: String, altEmail: String, handy: String, passwort: String)
