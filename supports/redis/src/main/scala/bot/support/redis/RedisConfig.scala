package bot.support.redis

import java.net.URI

import eu.timepit.refined.types.string.NonEmptyString

case class RedisConfig(uri: URI, prefix: NonEmptyString)
