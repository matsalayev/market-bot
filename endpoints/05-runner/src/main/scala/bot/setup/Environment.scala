package bot.setup

import cats.MonadThrow
import cats.effect.Async
import cats.effect.Resource
import cats.effect.std.Console
import cats.effect.std.Random
import dev.profunktor.redis4cats.Redis
import dev.profunktor.redis4cats.effect.Log.NoOp.instance
import eu.timepit.refined.pureconfig._
import org.typelevel.log4cats.Logger
import pureconfig.generic.auto.exportReader
import sttp.client3.httpclient.fs2.HttpClientFs2Backend

import bot.JobsEnvironment
import bot.Repositories
import bot.Services
import bot.auth.impl.Middlewares
import bot.http.{ Environment => ServerEnvironment }
import bot.integration.aws.s3.S3Client
import bot.integrations.telegram.TelegramClient
import bot.support.database.Migrations
import bot.support.redis.RedisClient
import bot.support.skunk.SkunkSession
import bot.utils.ConfigLoader

case class Environment[F[_]: Async: MonadThrow: Logger](
    config: Config,
    repositories: Repositories[F],
    services: Services[F],
    middlewares: Middlewares[F],
    s3Client: S3Client[F],
    redis: RedisClient[F],
    telegramClientMarket: TelegramClient[F],
    telegramClientAgent: TelegramClient[F],
  ) {
  lazy val jobsEnabled: Boolean = config.jobs.enabled
  lazy val toServer: ServerEnvironment[F] =
    ServerEnvironment(
      middlewares = middlewares,
      services = services,
      config = config.httpServer,
      s3Client = s3Client,
      telegramClientMarket = telegramClientMarket,
      telegramClientAgent = telegramClientAgent,
      redis = redis,
      telegramMarketBot = config.marketBot,
      telegramAgentBot = config.agentBot,
    )
  lazy val toJobs: JobsEnvironment[F] =
    JobsEnvironment(
      repos = JobsEnvironment.Repositories(),
      adminPhone = config.adminPhone,
    )
}

object Environment {
  def make[F[_]: Async: Console: Logger]: Resource[F, Environment[F]] =
    for {
      config <- Resource.eval(ConfigLoader.load[F, Config])
      _ <- Resource.eval(Migrations.run[F](config.migrations))

      redis <- Redis[F].utf8(config.redis.uri.toString).map(RedisClient[F](_, config.redis.prefix))
      repositories <- SkunkSession.make[F](config.database).map { implicit session =>
        Repositories.make[F]
      }

      implicit0(random: Random[F]) <- Resource.eval(Random.scalaUtilRandom)
      s3Client <- S3Client.resource(config.s3)
      telegramBrokerMarket <- HttpClientFs2Backend.resource[F]().map { implicit backend =>
        TelegramClient.make[F](config.marketBot)
      }
      telegramBrokerAgent <- HttpClientFs2Backend.resource[F]().map { implicit backend =>
        TelegramClient.make[F](config.agentBot)
      }
      services = Services
        .make[F](
          config.auth,
          repositories,
          redis,
          s3Client,
          telegramBrokerMarket,
          telegramBrokerAgent,
          config.appDomain,
        )
      middleware = Middlewares.make[F](config.auth, redis)
    } yield Environment[F](
      config,
      repositories,
      services,
      middleware,
      s3Client,
      redis,
      telegramBrokerMarket,
      telegramBrokerAgent,
    )
}
