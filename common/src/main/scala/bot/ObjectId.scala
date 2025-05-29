package bot

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

import bot.effects.IsUUID

case class ObjectId[A: IsUUID](
    id: A
  )

object ObjectId {
  implicit def entityEncoder[A: IsUUID: Encoder]: Encoder[ObjectId[A]] =
    deriveEncoder[ObjectId[A]]
}
