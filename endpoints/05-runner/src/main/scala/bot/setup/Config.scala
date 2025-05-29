package bot.setup

import eu.timepit.refined.types.string.NonEmptyString

import bot.Phone
import bot.auth.AuthConfig
import bot.integration.aws.s3.AWSConfig
import bot.integrations.telegram.TelegramBotsConfig
import bot.support.database.MigrationsConfig
import bot.support.http4s.HttpServerConfig
import bot.support.jobs.JobsRunnerConfig
import bot.support.redis.RedisConfig
import bot.support.skunk.DataBaseConfig

case class Config(
    httpServer: HttpServerConfig,
    database: DataBaseConfig,
    auth: AuthConfig,
    redis: RedisConfig,
    s3: AWSConfig,
    adminPhone: Phone,
    jobs: JobsRunnerConfig,
    marketBot: TelegramBotsConfig,
    appDomain: NonEmptyString,
  ) {
  lazy val migrations: MigrationsConfig = MigrationsConfig(
    hostname = database.host.value,
    port = database.port.value,
    database = database.database.value,
    username = database.user.value,
    password = database.password.value,
    schema = "public",
    location = "db/migration",
  )
}
