package bot.services

import bot.domain.PersonId
import bot.domain.corporate.CreateEmployee
import bot.domain.corporate.User
import bot.domain.enums.Role
import bot.effects.Calendar
import bot.effects.GenUUID
import bot.exception.AError
import bot.repositories.PeopleRepository
import bot.repositories.UsersRepository
import bot.repositories.dto.Person
import bot.syntax.refined._
import bot.utils.ID
import cats.MonadThrow
import cats.implicits._

trait EmployeeService[F[_]] {
  def create(data: CreateEmployee, createdBy: PersonId): F[Unit]
}

object EmployeeService {
  def make[F[_]: MonadThrow: GenUUID: Calendar](
      peopleRepository: PeopleRepository[F],
      usersRepository: UsersRepository[F],
    ): EmployeeService[F] =
    new EmployeeService[F] {
      override def create(data: CreateEmployee, createdBy: PersonId): F[Unit] =
        usersRepository
          .findById(createdBy)
          .flatMap(userOpt =>
            userOpt.fold(AError.BadRequest("Foydalanuvchi topilmadi").raiseError[F, Unit]) { user =>
              for {
                id <- ID.make[F, PersonId]
                now <- Calendar[F].currentZonedDateTime
                _ <- peopleRepository.create(
                  Person(
                    id = id,
                    createdAt = now,
                    fullName = data.name,
                    gender = data.gender,
                    dateOfBirth = None,
                    documentNumber = None,
                    pinflNumber = None,
                    updatedAt = None,
                    deletedAt = None,
                  )
                )
                _ <- usersRepository.createUser(
                  User(
                    id = id,
                    createdAt = now,
                    role = Role.Employee,
                    phone = data.phone,
                    assetId = None,
                    corporateId = user.corporateId,
                    password =
                      "$s0$e0801$5JK3Ogs35C2h5htbXQoeEQ==$N7HgNieSnOajn1FuEB7l4PhC6puBSq+e1E8WUaSJcGY=",
                  )
                )
              } yield ()
            }
          )
    }
}
