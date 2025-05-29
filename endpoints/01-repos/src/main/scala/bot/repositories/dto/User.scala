package bot.repositories.dto

import java.time.ZonedDateTime

import bot.Phone
import bot.domain.AssetId
import bot.domain.PersonId
import bot.domain.enums.Role
import eu.timepit.refined.types.string.NonEmptyString

case class User(
    id: PersonId,
    createdAt: ZonedDateTime,
    fullName: NonEmptyString,
    role: Role,
    photo: Option[AssetId],
    phone: Phone,
  )
