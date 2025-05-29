package bot.generators

import org.scalacheck.Gen

import bot.domain.asset.Asset
import bot.syntax.refined._

trait AssetGenerators { this: Generators =>
  def assetGen: Gen[Asset] =
    for {
      id <- assetIdGen
      now <- zonedDateTimeGen
      s3Key <- nonEmptyString
      filename <- nonEmptyString
      mediaType <- nonEmptyString
    } yield Asset(
      id = id,
      createdAt = now,
      s3Key = s3Key,
      fileName = Some(filename),
      contentType = Some(mediaType),
    )
}
