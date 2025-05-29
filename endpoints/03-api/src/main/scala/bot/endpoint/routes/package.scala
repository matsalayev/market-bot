package bot.endpoint

import bot.domain.auth.AuthedUser
import bot.domain.enums.Role
import org.http4s.AuthedRequest
import org.http4s.Request

package object routes {
  object asAdmin {
    def unapply[F[_]](ar: AuthedRequest[F, AuthedUser]): Option[(Request[F], AuthedUser)] =
      Option.when(ar.context.role == Role.Director)(
        ar.req -> ar.context
      )
  }
}
