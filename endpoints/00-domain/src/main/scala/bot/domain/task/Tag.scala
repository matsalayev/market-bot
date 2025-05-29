package bot.domain.task

import bot.domain.CorporateId
import bot.domain.TagId
import eu.timepit.refined.types.string.NonEmptyString

case class Tag(
    id: TagId,
    name: NonEmptyString,
    color: Option[NonEmptyString],
    corporateId: CorporateId,
  )
