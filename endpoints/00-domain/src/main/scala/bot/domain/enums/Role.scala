package bot.domain.enums

import enumeratum.EnumEntry.Snakecase
import enumeratum._

sealed trait Role extends Snakecase
object Role extends Enum[Role] with CirceEnum[Role] {
  case object Agent extends Role
  override def values: IndexedSeq[Role] = findValues
}
