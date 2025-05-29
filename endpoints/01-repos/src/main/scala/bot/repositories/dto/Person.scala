package bot.repositories.dto

import java.time.LocalDate
import java.time.ZonedDateTime

import bot.domain.PersonId
import bot.domain.enums.Gender
import eu.timepit.refined.types.string.NonEmptyString

case class Person(
    id: PersonId,
    createdAt: ZonedDateTime,
    fullName: NonEmptyString,
    gender: Gender,
    dateOfBirth: Option[LocalDate],
    documentNumber: Option[NonEmptyString],
    pinflNumber: Option[NonEmptyString],
    updatedAt: Option[ZonedDateTime],
    deletedAt: Option[ZonedDateTime],
  )
