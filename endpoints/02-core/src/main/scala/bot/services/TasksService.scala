package bot.services

import bot.domain.CorporateId
import bot.domain.ProjectId
import bot.domain.TagId
import bot.domain.task.CreateTag
import bot.domain.task.Tag
import bot.effects.Calendar
import bot.effects.GenUUID
import bot.repositories.TasksRepository
import bot.repositories.dto
import bot.utils.ID
import cats.MonadThrow
import cats.implicits._

trait TasksService[F[_]] {
  def createTag(tag: CreateTag, corporateId: CorporateId): F[Unit]
  def getAllTasks(projectId: ProjectId): F[List[dto.Task]]
}

object TasksService {
  def make[F[_]: MonadThrow: GenUUID: Calendar](
      tasksRepository: TasksRepository[F]
    ): TasksService[F] =
    new TasksService[F] {
      override def createTag(data: CreateTag, corporateId: CorporateId): F[Unit] = for {
        id <- ID.make[F, TagId]
        _ <- tasksRepository.createTag(
          Tag(id = id, name = data.name, color = data.color.some, corporateId = corporateId)
        )
      } yield ()

      override def getAllTasks(projectId: ProjectId): F[List[dto.Task]] =
        tasksRepository.getAll(projectId)
    }
}
