package bot.services

import cats.Applicative
import cats.Monad
import cats.effect.kernel.Sync
import cats.implicits.catsSyntaxApplicativeId
import cats.implicits.catsSyntaxOptionId
import cats.implicits.toFlatMapOps
import eu.timepit.refined.types.string.NonEmptyString
import org.typelevel.log4cats.Logger

import bot.Phone
import bot.domain.telegram.CallbackQuery
import bot.domain.telegram.Contact
import bot.domain.telegram.Message
import bot.domain.telegram.PhotoSize
import bot.domain.telegram.Update
import bot.domain.telegram.User
import bot.effects.Calendar
import bot.effects.GenUUID
import bot.integration.aws.s3.S3Client
import bot.integrations.telegram.TelegramClient
import bot.integrations.telegram.domain.MenuButtonWebApp
import bot.integrations.telegram.domain.WebAppInfo
import bot.repositories.AssetsRepository
import bot.repositories.PeopleRepository
import bot.repositories.TelegramRepository
import bot.repositories.UsersRepository
import bot.support.redis.RedisClient
import bot.syntax.refined.commonSyntaxAutoRefineV

trait MarketBotService[F[_]] {
  def telegramMessage(update: Update): F[Unit]
}

object MarketBotService {
  def make[F[_]: Monad: GenUUID: Calendar: Sync](
      telegramClient: TelegramClient[F],
      telegramRepository: TelegramRepository[F],
      peopleRepository: PeopleRepository[F],
      usersRepository: UsersRepository[F],
      assetsRepository: AssetsRepository[F],
      s3Client: S3Client[F],
      redis: RedisClient[F],
      appDomain: NonEmptyString,
    )(implicit
      logger: Logger[F]
    ): MarketBotService[F] = new MarketBotService[F] {
    override def telegramMessage(update: Update): F[Unit] =
      update match {
        case Update(_, Some(message), _) => handleMessage(message)
        case Update(_, _, Some(callbackQuery)) => handleCallbackQuery(callbackQuery)
        case _ => logger.info("unknown update type")
      }

    private def handleMessage(message: Message): F[Unit] =
      message match {
        case Message(_, Some(user), Some(text), None, None, None, None, None) =>
          handleTextMessage(user, text)
        case Message(_, Some(user), None, Some(contact), None, None, None, None) =>
          handleContactMessage(user, contact)
        case Message(_, Some(user), None, None, Some(photos), _, mediaGroupId, None) =>
          handlePhotoMessage(user, photos.maxBy(_.width), mediaGroupId)
        case Message(_, Some(user), None, None, None, None, None, Some(location)) =>
          handleLocationMessage(user, location.latitude, location.longitude)
        case _ => logger.info("undefined behaviour for customer bot")
      }

    private def handleTextMessage(user: User, text: String): F[Unit] =
      text match {
        case "/start" => sendMenu(user)
        case _ => logger.info("undefined behaviour for market bot")
      }

    private def handleContactMessage(user: User, contact: Contact): F[Unit] =
      contact match {
        case Contact(phoneNumberStr, Some(userTelegramId)) if user.id == userTelegramId =>
          val phoneNumber: Phone =
            if (phoneNumberStr.startsWith("+")) phoneNumberStr else s"+$phoneNumberStr"

          usersRepository.findByPhone(phoneNumber).flatMap {
            case Some(user) => Applicative[F].unit
            case _ => Applicative[F].unit
          }

        case _ => Applicative[F].unit
      }

    private def handleCallbackQuery(callbackQuery: CallbackQuery): F[Unit] =
      callbackQuery match {
        case CallbackQuery(_, Some(user), _, Some(message), Some(data)) => ().pure[F]
        case _ => logger.warn("unknown callback query structure")
      }

    private def handlePhotoMessage(
        user: User,
        photoSize: PhotoSize,
        mediaGroupId: Option[String],
      ): F[Unit] = ???

    private def handleLocationMessage(
        user: User,
        latitude: Double,
        longitude: Double,
      ): F[Unit] = ???

    private def sendMenu(user: User): F[Unit] =
      telegramClient.setChatMenuButton(
        user.id,
        MenuButtonWebApp("web_app", "Open", WebAppInfo(s"$appDomain/forms/index.html")).some,
      )
  }
}
