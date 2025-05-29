package bot.repositories.dto

import java.time.ZonedDateTime

import eu.timepit.refined.types.string.NonEmptyString

import bot.Phone
import bot.domain.AssetId
import bot.domain.PersonId
import bot.domain.enums.Role

case class User(
    id: PersonId,
    createdAt: ZonedDateTime,
    fullName: NonEmptyString,
    role: Role,
    photo: Option[AssetId],
    phone: Phone,
  )
