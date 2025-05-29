package bot.domain.corporate

import bot.domain.LocationId
import eu.timepit.refined.types.string.NonEmptyString

case class Location(
    id: LocationId,
    name: NonEmptyString,
    latitude: Double,
    longitude: Double,
  )
