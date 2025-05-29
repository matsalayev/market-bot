package bot.http

import bot.Services
import bot.auth.impl.Middlewares
import bot.integration.aws.s3.S3Client
import bot.integrations.telegram.TelegramBotsConfig
import bot.integrations.telegram.TelegramClient
import bot.support.http4s.HttpServerConfig
import bot.support.redis.RedisClient
import cats.effect.Async

case class Environment[F[_]: Async](
    config: HttpServerConfig,
    telegramCorporateBot: TelegramBotsConfig,
    telegramEmployeeBot: TelegramBotsConfig,
    middlewares: Middlewares[F],
    services: Services[F],
    s3Client: S3Client[F],
    telegramClientEmployee: TelegramClient[F],
    telegramClientCorporate: TelegramClient[F],
    redis: RedisClient[F],
  )
