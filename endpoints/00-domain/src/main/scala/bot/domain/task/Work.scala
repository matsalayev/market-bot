package bot.domain.task

import java.time.ZonedDateTime

import bot.domain.PersonId
import bot.domain.TaskId
import bot.domain.WorkId
import eu.timepit.refined.types.all.NonNegBigInt

case class Work(
    id: WorkId,
    createdAt: ZonedDateTime,
    userId: PersonId,
    taskId: TaskId,
    duringMinutes: NonNegBigInt,
    finishedAt: Option[ZonedDateTime],
  )
