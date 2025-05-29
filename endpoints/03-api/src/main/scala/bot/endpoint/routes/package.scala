package bot.endpoint

import org.http4s.AuthedRequest
import org.http4s.Request

import bot.domain.auth.AuthedUser
import bot.domain.enums.Role

package object routes {
  object asAdmin {
    def unapply[F[_]](ar: AuthedRequest[F, AuthedUser]): Option[(Request[F], AuthedUser)] =
      Option.when(ar.context.role == Role.Agent)(
        ar.req -> ar.context
      )
  }
}
