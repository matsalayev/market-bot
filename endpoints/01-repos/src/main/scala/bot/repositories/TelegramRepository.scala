package bot.repositories

import bot.domain.PersonId
import bot.domain.telegram.BotUser
import bot.effects.Calendar
import bot.repositories.dto.User
import bot.repositories.sql.TelegramSql
import bot.support.skunk.syntax.all.skunkSyntaxCommandOps
import bot.support.skunk.syntax.all.skunkSyntaxQueryOps
import cats.effect.Resource
import eu.timepit.refined.types.string.NonEmptyString
import skunk._

trait TelegramRepository[F[_]] {
  def createBotUser(user: BotUser): F[Unit]
  def findByChatId(chatId: Long): F[Option[PersonId]]
  def findUser(chatId: Long): F[Option[User]]
  def findCorporateName(chatId: Long): F[Option[NonEmptyString]]
}

object TelegramRepository {
  def make[F[_]: fs2.Compiler.Target: Calendar](
      implicit
      session: Resource[F, Session[F]]
    ): TelegramRepository[F] = new TelegramRepository[F] {
    override def createBotUser(user: BotUser): F[Unit] =
      TelegramSql.insertBotUser.execute(user)

    override def findByChatId(chatId: Long): F[Option[PersonId]] =
      TelegramSql.findById.queryOption(chatId)

    override def findCorporateName(chatId: Long): F[Option[NonEmptyString]] =
      TelegramSql.findCorporateName.queryOption(chatId)

    override def findUser(chatId: Long): F[Option[User]] =
      TelegramSql.findUser.queryOption(chatId)
  }
}
