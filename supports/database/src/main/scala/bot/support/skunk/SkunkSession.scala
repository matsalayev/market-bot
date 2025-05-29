package bot.support.skunk

import bot.support.skunk.syntax.all.skunkSyntaxConnectionOps
import bot.syntax.refined.commonSyntaxAutoUnwrapV
import cats.effect.Temporal
import cats.effect.std.Console
import fs2.io.net.Network
import natchez.Trace.Implicits.noop
import org.typelevel.log4cats.Logger
import skunk.Session
import skunk.SessionPool
import skunk.util.Typer

object SkunkSession {
  def make[F[_]: Temporal: Logger: Network: Console](config: DataBaseConfig): SessionPool[F] =
    Session
      .pooled[F](
        host = config.host,
        port = config.port,
        user = config.user,
        password = Some(config.password.value),
        database = config.database,
        max = config.poolSize,
        strategy = Typer.Strategy.SearchPath,
      )
      .evalTap(_.checkPostgresConnection)
}
