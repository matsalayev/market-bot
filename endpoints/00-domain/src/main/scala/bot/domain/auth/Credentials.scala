package bot.domain.auth

import bot.Phone
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.generic.JsonCodec
import io.circe.refined._

@JsonCodec
case class Credentials(phone: Phone, password: NonEmptyString)
