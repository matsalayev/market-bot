package bot.endpoint.routes

import bot.domain.auth.AuthedUser
import bot.domain.corporate.CreateEmployee
import bot.effects.FileLoader
import bot.services.EmployeeService
import bot.support.http4s.utils.Routes
import bot.support.syntax.all.http4SyntaxReqOps
import cats.effect.Concurrent
import cats.implicits._
import org.http4s._
import org.http4s.circe.JsonDecoder
import org.typelevel.log4cats.Logger

final case class FormsRoutes[F[_]: Concurrent: FileLoader: JsonDecoder](
    employeeService: EmployeeService[F]
  )(implicit
    logger: Logger[F]
  ) extends Routes[F, AuthedUser] {
  override val path = "/forms"

  override val public: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "login" =>
      FileLoader[F]
        .resourceAsString("forms/login.html")
        .flatMap(content =>
          Ok(content.trim).map(_.withContentType(headers.`Content-Type`(MediaType.text.html)))
        )

    case GET -> Root / "create-employee" / _ =>
      FileLoader[F]
        .resourceAsString("forms/create-employee.html")
        .flatMap(content =>
          Ok(content.trim).map(_.withContentType(headers.`Content-Type`(MediaType.text.html)))
        )
  }

  override val `private`: AuthedRoutes[AuthedUser, F] = AuthedRoutes.of {

    case ar @ POST -> Root / "create-employee" / "submit" as user =>
      ar.req
        .decodeR[CreateEmployee] { data =>
          employeeService.create(data, user.id).flatMap(Created(_))
        }
        .handleErrorWith { error =>
          BadRequest(error.getMessage)
        }
  }
}
