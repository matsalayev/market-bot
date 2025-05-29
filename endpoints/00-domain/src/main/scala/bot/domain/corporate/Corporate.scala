package bot.domain.corporate

import java.time.ZonedDateTime

import bot.domain.AssetId
import bot.domain.CorporateId
import bot.domain.LocationId
import eu.timepit.refined.types.string.NonEmptyString

case class Corporate(
    id: CorporateId,
    createdAt: ZonedDateTime,
    name: NonEmptyString,
    locationId: LocationId,
    photo: Option[AssetId],
  )
