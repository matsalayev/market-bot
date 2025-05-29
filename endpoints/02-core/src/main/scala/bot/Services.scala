package bot

import bot.auth.AuthConfig
import bot.auth.impl.Auth
import bot.domain.auth.AccessCredentials
import bot.domain.auth.AuthedUser
import bot.integration.aws.s3.S3Client
import bot.integrations.telegram.TelegramClient
import bot.services._
import bot.support.redis.RedisClient
import cats.data.OptionT
import cats.effect.Async
import cats.effect.std.Random
import eu.timepit.refined.types.string.NonEmptyString
import org.typelevel.log4cats.Logger

case class Services[F[_]](
    auth: Auth[F, AuthedUser],
    assets: AssetsService[F],
    marketBotService: MarketBotService[F],
  )

object Services {
  def make[F[_]: Async: Logger: Random](
      config: AuthConfig,
      repositories: Repositories[F],
      redis: RedisClient[F],
      s3Client: S3Client[F],
      telegramClientCorporate: TelegramClient[F],
      telegramClientEmployee: TelegramClient[F],
      appDomain: NonEmptyString,
    ): Services[F] = {
    def findUser: Phone => F[Option[AccessCredentials[AuthedUser]]] = phone =>
      OptionT(repositories.users.find(phone))
        .map(identity[AccessCredentials[AuthedUser]])
        .value

    Services[F](
      auth = Auth.make[F, AuthedUser](config.user, findUser, redis),
      assets = AssetsService.make[F](
        repositories.assetsRepository,
        s3Client,
      ),
      marketBotService = MarketBotService.make[F](
        telegramClientCorporate,
        repositories.telegramRepository,
        repositories.people,
        repositories.users,
        repositories.assetsRepository,
        s3Client,
        redis,
        appDomain,
      ),
    )
  }
}
