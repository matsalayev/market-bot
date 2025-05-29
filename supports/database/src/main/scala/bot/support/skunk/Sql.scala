package bot.support.skunk

import bot.effects.IsUUID
import bot.support.skunk.codecs.identification
import skunk.Codec

abstract class Sql[T: IsUUID] {
  val id: Codec[T] = identification[T]
}
