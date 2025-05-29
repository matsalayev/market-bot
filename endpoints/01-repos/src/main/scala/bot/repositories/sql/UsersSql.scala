package bot.repositories.sql

import shapeless.HNil
import skunk._
import skunk.implicits._

import bot.Phone
import bot.domain.PersonId
import bot.domain.auth.AccessCredentials
import bot.domain.auth.AuthedUser.User
import bot.repositories.dto
import bot.support.skunk.Sql
import bot.support.skunk.codecs.nes
import bot.support.skunk.codecs.phone
import bot.support.skunk.codecs.zonedDateTime

private[repositories] object UsersSql extends Sql[PersonId] {
  private val codec = (id *: role *: phone).to[User]
  private[repositories] val dtoUserCodec =
    (id *: zonedDateTime *: nes *: role *: AssetsSql.id.opt *: phone)
      .to[dto.User]

  private val personDecoder: Decoder[AccessCredentials[User]] =
    (codec *: passwordHash).map {
      case user *: hash *: HNil =>
        AccessCredentials(
          data = user,
          password = hash,
        )
    }

  val findByLogin: Query[Phone, AccessCredentials[User]] =
    sql"""
      SELECT
        id, role, phone, password
      FROM users
      WHERE
        phone = $phone
        AND deleted_at IS NULL
    """.query(personDecoder)

  val insert: Command[AccessCredentials[User]] =
    sql"""
      INSERT INTO users (id, role, phone, password)
      VALUES ($id, $role, $phone, $passwordHash)
    """
      .command
      .contramap { (u: AccessCredentials[User]) =>
        u.data.id *: u.data.role *: u.data.phone *: u.password *: EmptyTuple
      }

  val delete: Command[PersonId] =
    sql"""DELETE FROM users WHERE id = $id""".command
}
