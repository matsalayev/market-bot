package bot.domain.enums

import enumeratum.EnumEntry.Snakecase
import enumeratum._

sealed trait BotType extends Snakecase
object BotType extends Enum[BotType] with CirceEnum[BotType] {
  case object Agent extends BotType
  case object Market extends BotType
  override def values: IndexedSeq[BotType] = findValues
}
