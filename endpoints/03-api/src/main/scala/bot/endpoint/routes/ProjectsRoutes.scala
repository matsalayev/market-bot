package bot.endpoint.routes

import bot.domain.auth.AuthedUser
import bot.effects.FileLoader
import bot.services.ProjectsService
import bot.support.http4s.utils.Routes
import cats.effect.Concurrent
import org.http4s._
import org.http4s.circe.JsonDecoder

final case class ProjectsRoutes[F[_]: Concurrent: FileLoader: JsonDecoder](
    projects: ProjectsService[F]
  ) extends Routes[F, AuthedUser] {
  override val path = "/projects"

  override val public: HttpRoutes[F] = HttpRoutes.empty

  override val `private`: AuthedRoutes[AuthedUser, F] = AuthedRoutes.empty
}
