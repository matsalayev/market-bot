package bot.repositories.dto

import java.time.ZonedDateTime

import bot.domain.AssetId
import bot.domain.ProjectId
import bot.domain.TaskId
import bot.domain.enums.TaskStatus
import eu.timepit.refined.types.string.NonEmptyString

case class Task(
    id: TaskId,
    createdAt: ZonedDateTime,
    createdBy: NonEmptyString,
    projectId: ProjectId,
    projectName: NonEmptyString,
    name: NonEmptyString,
    description: Option[NonEmptyString],
    tagName: Option[NonEmptyString],
    tagColor: Option[NonEmptyString],
    assetId: Option[AssetId],
    status: TaskStatus,
    deadline: Option[ZonedDateTime],
    link: Option[NonEmptyString],
  )
