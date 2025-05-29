package bot.repositories.sql

import bot.domain.AssetId
import bot.domain.asset.Asset
import bot.support.skunk.Sql
import bot.support.skunk.codecs.nes
import bot.support.skunk.codecs.zonedDateTime
import skunk._
import skunk.implicits._

private[repositories] object AssetsSql extends Sql[AssetId] {
  private val codec: Codec[Asset] = (id *: zonedDateTime *: nes *: nes.opt *: nes.opt).to[Asset]

  val insert: Command[Asset] =
    sql"""INSERT INTO assets VALUES ($codec)""".command

  val findById: Query[AssetId, Asset] =
    sql"""SELECT * FROM assets WHERE id = $id LIMIT 1""".query(codec)
}
