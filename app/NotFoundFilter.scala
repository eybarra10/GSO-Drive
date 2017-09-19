import javax.inject._

import akka.stream.Materializer
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class NotFoundFilter @Inject()(val mat: Materializer) extends Filter {

  override def apply(nextFilter: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    nextFilter(requestHeader) map { result =>
      if (result.header.status == 404) {
        implicit val header = requestHeader
        Results.NotFound(views.html.notFound.apply())
      } else {
        result
      }
    }
  }
}
