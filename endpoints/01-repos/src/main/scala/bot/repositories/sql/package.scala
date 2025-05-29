package bot.repositories

import skunk._
import skunk.codec.all._
import skunk.data.Type
import tsec.passwordhashers.PasswordHash
import tsec.passwordhashers.jca.SCrypt

import bot.domain.enums._

package object sql {
  val gender: Codec[Gender] = `enum`[Gender](Gender, Type("gender"))
  val role: Codec[Role] = `enum`[Role](Role, Type("role"))

  val passwordHash: Codec[PasswordHash[SCrypt]] =
    varchar.imap[PasswordHash[SCrypt]](PasswordHash[SCrypt])(identity)
}
