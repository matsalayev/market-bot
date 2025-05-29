package bot
package support.skunk

import java.time.ZonedDateTime

import bot.effects.IsUUID
import bot.syntax.refined.commonSyntaxAutoRefineV
import eu.timepit.refined.types.string.NonEmptyString
import skunk.Codec
import skunk.codec.all.timestamptz
import skunk.codec.all.uuid
import skunk.codec.all.varchar

package object codecs {
  def identification[A: IsUUID]: Codec[A] = uuid.imap[A](IsUUID[A].uuid.get)(IsUUID[A].uuid.apply)

  val nes: Codec[NonEmptyString] = varchar.imap[NonEmptyString](identity(_))(_.value)
  val digits: Codec[Digits] = varchar.imap[Digits](identity(_))(_.value)
  val phone: Codec[Phone] = varchar.imap[Phone](identity(_))(_.value)
  val zonedDateTime: Codec[ZonedDateTime] = timestamptz.imap(_.toZonedDateTime)(_.toOffsetDateTime)
}
