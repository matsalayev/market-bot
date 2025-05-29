package bot.repositories

import bot.Phone
import bot.domain.auth.AccessCredentials
import bot.domain.auth.AuthedUser.User
import bot.repositories.sql.UsersSql
import bot.support.skunk.syntax.all._
import cats.effect.Resource
import skunk._

trait UsersRepository[F[_]] {
  def find(phone: Phone): F[Option[AccessCredentials[User]]]
  def create(userAndHash: AccessCredentials[User]): F[Unit]
  def findByPhone(phone: Phone): F[Option[User]] = ???
}

object UsersRepository {
  def make[F[_]: fs2.Compiler.Target](
      implicit
      session: Resource[F, Session[F]]
    ): UsersRepository[F] = new UsersRepository[F] {
    override def find(phone: Phone): F[Option[AccessCredentials[User]]] =
      UsersSql.findByLogin.queryOption(phone)

    override def create(userAndHash: AccessCredentials[User]): F[Unit] =
      UsersSql.insert.execute(userAndHash)
  }
}
