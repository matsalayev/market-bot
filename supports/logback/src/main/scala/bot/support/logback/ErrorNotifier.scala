package bot.support.logback

import bot.support.logback.requests.SendError
import bot.support.sttp.SttpBackends
import bot.support.sttp.SttpClient
import bot.support.sttp.SttpClientAuth
import cats.effect.Sync
import cats.implicits._
import org.typelevel.log4cats.Logger

trait ErrorNotifier[F[_]] {
  def sendNotification(error: String): F[Unit]
}

object ErrorNotifier {
  def apply[F[_]: Sync: SttpBackends.Simple](
      config: MonitoringConfig
    )(implicit
      logger: Logger[F]
    ): ErrorNotifier[F] = new ErrorNotifier[F] {
    private lazy val client: SttpClient.CirceJson[F] = SttpClient.circeJson(
      config.telegramAlert.apiUrl,
      SttpClientAuth.withUriParams(
        "chat_id" -> config.telegramAlert.chatId
      ),
    )

    override def sendNotification(error: String): F[Unit] =
      (for {
        _ <- logger.info(s"Error handled via notifier appender: $error")
        _ <- client.request(SendError(error)).whenA(config.telegramAlert.enabled)
      } yield ())
        .onError { throwable =>
          logger.warn(throwable)("Error occurred while send notification email")
        }
  }
}
