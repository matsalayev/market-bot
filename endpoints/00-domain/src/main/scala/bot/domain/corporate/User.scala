package bot.domain.corporate

import java.time.ZonedDateTime

import bot.Phone
import bot.domain.AssetId
import bot.domain.CorporateId
import bot.domain.PersonId
import bot.domain.enums.Role
import eu.timepit.refined.types.string.NonEmptyString

case class User(
    id: PersonId,
    createdAt: ZonedDateTime,
    role: Role,
    phone: Phone,
    assetId: Option[AssetId],
    corporateId: CorporateId,
    password: NonEmptyString,
  )
