package bot.domain.corporate

import bot.Phone
import bot.domain.enums.Gender
import bot.syntax.refined._
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.generic.JsonCodec
import io.circe.refined._

@JsonCodec
case class CreateEmployee(
    name: NonEmptyString,
    gender: Gender,
    phone: Phone,
  )
