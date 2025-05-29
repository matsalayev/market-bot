package bot.domain.task

import eu.timepit.refined.types.string.NonEmptyString

case class CreateTag(
    name: NonEmptyString,
    color: NonEmptyString,
  )
