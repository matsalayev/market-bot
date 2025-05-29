package bot.support.logback

import java.net.URI

import bot.support.logback.MonitoringConfig.TelegramConfig

case class MonitoringConfig(telegramAlert: TelegramConfig)

object MonitoringConfig {
  case class TelegramConfig(
      apiUrl: URI,
      chatId: String,
      enabled: Boolean,
    )
}
