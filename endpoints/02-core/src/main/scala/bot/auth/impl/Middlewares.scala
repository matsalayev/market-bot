package bot.auth.impl

import cats.effect._
import org.http4s.server.AuthMiddleware
import org.typelevel.log4cats.Logger

import bot.auth.AuthConfig
import bot.domain.auth.AuthedUser
import bot.support.redis.RedisClient

object Middlewares {
  def make[F[_]: Sync: Logger](
      config: AuthConfig,
      redis: RedisClient[F],
    ): Middlewares[F] =
    new Middlewares[F](
      LiveMiddleware.make[F, AuthedUser](config.user, redis)
    )
}

final class Middlewares[F[_]] private (
    val user: AuthMiddleware[F, AuthedUser]
  )
