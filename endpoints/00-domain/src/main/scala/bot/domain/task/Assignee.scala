package bot.domain.task

import bot.domain.PersonId
import bot.domain.TaskId

case class Assignee(
    taskId: TaskId,
    employeeId: List[PersonId],
  )
