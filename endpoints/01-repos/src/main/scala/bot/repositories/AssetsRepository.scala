package bot.repositories

import bot.domain.AssetId
import bot.domain.asset.Asset
import bot.repositories.sql.AssetsSql
import bot.support.skunk.syntax.all._
import cats.effect.Resource
import skunk._

trait AssetsRepository[F[_]] {
  def create(asset: Asset): F[Unit]
  def findAsset(assetId: AssetId): F[Option[Asset]]
}

object AssetsRepository {
  def make[F[_]: fs2.Compiler.Target](
      implicit
      session: Resource[F, Session[F]]
    ): AssetsRepository[F] = new AssetsRepository[F] {
    override def create(asset: Asset): F[Unit] =
      AssetsSql.insert.execute(asset)

    override def findAsset(assetId: AssetId): F[Option[Asset]] =
      AssetsSql.findById.queryOption(assetId)
  }
}
