package bot.integrations.telegram.requests

import sttp.model.Method
import bot.integrations.telegram.domain.GetFileResponse
import bot.support.sttp.SttpRequest

case class GetFile(
    fileId: String
  )

object GetFile {
  implicit val sttpRequest: SttpRequest[GetFile, GetFileResponse] =
    new SttpRequest[GetFile, GetFileResponse] {
      val method: Method = Method.GET
      override def path: Path = r => s"getFile"
      override def params: Params = r => Map("file_id" -> r.fileId)

      def body: Body = noBody
    }
}
