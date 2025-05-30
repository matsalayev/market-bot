package bot.endpoint.routes

import cats.effect.Concurrent
import cats.implicits._
import org.http4s._
import org.http4s.circe.JsonDecoder

import bot.domain.auth.AuthedUser
import bot.effects.FileLoader
import bot.support.http4s.utils.Routes

final case class FormsRoutes[F[_]: Concurrent: FileLoader: JsonDecoder]()
    extends Routes[F, AuthedUser] {
  override val path = "/forms"

  override val public: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "products" =>
      Ok(FileLoader[F].resourceAsString("public/index.html"))
        .map(_.withContentType(headers.`Content-Type`(MediaType.text.html)))

    case GET -> Root / "css" / file =>
      Ok(FileLoader[F].resourceAsString(s"public/css/$file"))
        .map(_.withContentType(headers.`Content-Type`(MediaType.text.css)))

    case GET -> Root / "js" / file =>
      Ok(FileLoader[F].resourceAsString(s"public/js/$file"))
        .map(_.withContentType(headers.`Content-Type`(MediaType.application.javascript)))

    case GET -> Root / "img" / file =>
      Ok(FileLoader[F].resourceAsF2Stream(s"public/img/$file"))
        .map(_.withContentType(headers.`Content-Type`(MediaType.image.png)))
  }

  override val `private`: AuthedRoutes[AuthedUser, F] = AuthedRoutes.empty
}
