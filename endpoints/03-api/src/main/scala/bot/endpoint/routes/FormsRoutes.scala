package bot.endpoint.routes

import cats.effect.Concurrent
import cats.implicits._
import io.circe.Json
import org.http4s._
import org.http4s.circe.JsonDecoder
import org.http4s.circe._

import bot.Language
import bot.domain.auth.AuthedUser
import bot.effects.FileLoader
import bot.services.AssetsService
import bot.support.http4s.utils.Routes
import bot.support.syntax.all.http4SyntaxReqOps

final case class FormsRoutes[F[_]: Concurrent: FileLoader: JsonDecoder](
    assetsService: AssetsService[F]
  ) extends Routes[F, AuthedUser] {
  override val path = "/forms"

  override val public: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "products" =>
      Ok(
        Json.arr(
          Json.obj(
            "id" -> Json.fromInt(1),
            "name" -> Json.fromString("Coca-Cola"),
            "price" -> Json.fromInt(10000),
            "imageUrl" -> Json.fromString("img/cola.png"),
          ),
          Json.obj(
            "id" -> Json.fromInt(2),
            "name" -> Json.fromString("Chocolate"),
            "price" -> Json.fromInt(15000),
            "imageUrl" -> Json.fromString("img/choco.png"),
          ),
        )
      )

    case ar @ GET -> Root / html =>
      implicit val lang: Language = ar.lang
      Ok(assetsService.getFile(s"public/$html"))
        .map(_.withContentType(headers.`Content-Type`(MediaType.text.html)))

    case GET -> Root / "css" / file =>
      Ok(FileLoader[F].resourceAsString(s"public/css/$file"))
        .map(_.withContentType(headers.`Content-Type`(MediaType.text.css)))

    case ar @ GET -> Root / "js" / file =>
      implicit val lang: Language = ar.lang
      Ok(assetsService.getFile(s"public/js/$file"))
        .map(_.withContentType(headers.`Content-Type`(MediaType.application.javascript)))

    case GET -> Root / "img" / file =>
      Ok(FileLoader[F].resourceAsF2Stream(s"public/img/$file"))
        .map(_.withContentType(headers.`Content-Type`(MediaType.image.png)))
  }

  override val `private`: AuthedRoutes[AuthedUser, F] = AuthedRoutes.empty
}
