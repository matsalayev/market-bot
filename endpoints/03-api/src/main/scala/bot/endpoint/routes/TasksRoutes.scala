package bot.endpoint.routes

import bot.domain.auth.AuthedUser
import bot.effects.FileLoader
import bot.services.ProjectsService
import bot.support.http4s.utils.Routes
import cats.effect.Concurrent
import org.http4s._
import org.http4s.circe.JsonDecoder
import org.typelevel.log4cats.Logger

final case class TasksRoutes[F[_]: Concurrent: FileLoader: JsonDecoder](
    projects: ProjectsService[F]
  )(implicit
    logger: Logger[F]
  ) extends Routes[F, AuthedUser] {
  override val path = "/tasks"

  override val public: HttpRoutes[F] = HttpRoutes.empty

  override val `private`: AuthedRoutes[AuthedUser, F] = AuthedRoutes.empty
}
