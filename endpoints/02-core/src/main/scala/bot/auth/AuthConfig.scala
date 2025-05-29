package bot.auth

import bot.domain.JwtAccessTokenKey
import bot.domain.TokenExpiration

case class AuthConfig(user: AuthConfig.UserAuthConfig)

object AuthConfig {
  case class UserAuthConfig(
      tokenKey: JwtAccessTokenKey,
      accessTokenExpiration: TokenExpiration,
      refreshTokenExpiration: TokenExpiration,
    )
}
