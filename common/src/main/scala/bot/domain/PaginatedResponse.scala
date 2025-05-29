package bot.domain

import io.circe.generic.JsonCodec

import bot.syntax.circe._

@JsonCodec
case class PaginatedResponse[A](
    data: List[A],
    total: Long,
  )
