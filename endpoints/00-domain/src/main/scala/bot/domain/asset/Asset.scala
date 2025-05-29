package bot.domain.asset

import java.time.ZonedDateTime

import bot.domain.AssetId
import bot.syntax.circe._
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.generic.JsonCodec
import io.circe.refined._

@JsonCodec
case class Asset(
    id: AssetId,
    createdAt: ZonedDateTime,
    s3Key: NonEmptyString,
    fileName: Option[NonEmptyString],
    contentType: Option[NonEmptyString],
  )
