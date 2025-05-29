package bot.repositories.dto

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
    locationName: NonEmptyString,
    latitude: Double,
    longitude: Double,
    photo: Option[AssetId],
  )
