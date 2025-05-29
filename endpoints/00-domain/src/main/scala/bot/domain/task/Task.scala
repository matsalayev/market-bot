package bot.domain.task

import java.time.ZonedDateTime

import bot.domain.AssetId
import bot.domain.PersonId
import bot.domain.ProjectId
import bot.domain.TagId
import bot.domain.TaskId
import bot.domain.enums.TaskStatus
import eu.timepit.refined.types.string.NonEmptyString

case class Task(
    id: TaskId,
    createdAt: ZonedDateTime,
    createdBy: PersonId,
    projectId: ProjectId,
    name: NonEmptyString,
    description: Option[NonEmptyString],
    tagId: Option[TagId],
    photo: Option[AssetId],
    status: TaskStatus,
    deadline: Option[ZonedDateTime],
    link: Option[NonEmptyString],
  )
