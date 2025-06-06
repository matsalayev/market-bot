package bot.domain.auth

import io.circe.generic.JsonCodec
import io.circe.refined._

import bot.Phone
import bot.domain.PersonId
import bot.domain.enums.Role
import bot.syntax.circe._

@JsonCodec
sealed trait AuthedUser {
  val id: PersonId
  val role: Role
  val phone: Phone
}

object AuthedUser {
  @JsonCodec
  case class User(
      id: PersonId,
      role: Role,
      phone: Phone,
    ) extends AuthedUser
}
