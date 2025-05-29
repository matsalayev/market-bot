package bot.support.skunk

import skunk.Codec

import bot.effects.IsUUID
import bot.support.skunk.codecs.identification

abstract class Sql[T: IsUUID] {
  val id: Codec[T] = identification[T]
}
