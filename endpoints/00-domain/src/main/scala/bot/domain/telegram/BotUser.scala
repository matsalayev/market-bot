package bot.domain.telegram

import bot.domain.PersonId

case class BotUser(
    parentId: PersonId,
    chatId: Long,
  )
