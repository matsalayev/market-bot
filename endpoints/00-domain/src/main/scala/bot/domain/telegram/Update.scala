package bot.domain.telegram

import io.circe.generic.extras.Configuration
import io.circe.generic.extras.ConfiguredJsonCodec

@ConfiguredJsonCodec
case class Update(
    updateId: Int,
    message: Option[Message],
    callbackQuery: Option[CallbackQuery],
  )

object Update {
  implicit val configuration: Configuration = Configuration.default.withSnakeCaseMemberNames
}
