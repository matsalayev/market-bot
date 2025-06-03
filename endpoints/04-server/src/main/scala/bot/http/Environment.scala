package bot.http

import cats.effect.Async

import bot.Services
import bot.auth.impl.Middlewares
import bot.integration.aws.s3.S3Client
import bot.integrations.telegram.TelegramBotsConfig
import bot.integrations.telegram.TelegramClient
import bot.support.http4s.HttpServerConfig
import bot.support.redis.RedisClient

case class Environment[F[_]: Async](
    config: HttpServerConfig,
    telegramMarketBot: TelegramBotsConfig,
    telegramAgentBot: TelegramBotsConfig,
    middlewares: Middlewares[F],
    services: Services[F],
    s3Client: S3Client[F],
    telegramClientMarket: TelegramClient[F],
    telegramClientAgent: TelegramClient[F],
    redis: RedisClient[F],
  )
