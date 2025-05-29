package bot.domain.task

import java.time.ZonedDateTime

import bot.domain.AssetId
import bot.domain.ProjectId
import bot.domain.TagId
import bot.domain.enums.TaskStatus
import eu.timepit.refined.types.string.NonEmptyString

case class CreateTask(
    projectId: ProjectId,
    name: NonEmptyString,
    description: Option[NonEmptyString],
    tagId: Option[TagId],
    photo: Option[AssetId],
    status: TaskStatus,
    deadline: Option[ZonedDateTime],
  )
