import javax.inject.Inject

import play.api.http.HttpFilters
import play.api.mvc.EssentialFilter

class Filters @Inject() (notfound: NotFoundFilter) extends HttpFilters {

  override def filters: Seq[EssentialFilter] = {
    Seq(notfound)
  }
}
