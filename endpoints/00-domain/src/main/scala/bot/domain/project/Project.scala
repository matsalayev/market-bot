package bot.domain.project

import java.time.ZonedDateTime

import bot.domain.CorporateId
import bot.domain.PersonId
import bot.domain.ProjectId
import bot.syntax.circe._
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.generic.JsonCodec
import io.circe.refined._

@JsonCodec
case class Project(
    id: ProjectId,
    createdAt: ZonedDateTime,
    createdBy: PersonId,
    corporateId: CorporateId,
    name: NonEmptyString,
    description: Option[NonEmptyString],
  )
