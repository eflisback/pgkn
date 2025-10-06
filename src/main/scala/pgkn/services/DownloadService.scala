package pgkn.services

import org.scalajs.dom
import org.scalajs.dom.document

object DownloadService:
  def downloadIcs(content: String, fileName: String = "handledartider.ics"): Unit =
    val blob = new dom.Blob(
      scala.scalajs.js.Array(content),
      new dom.BlobPropertyBag { `type` = "text/calendar" }
    )
    val url = dom.URL.createObjectURL(blob)

    val anchor = document.createElement("a").asInstanceOf[dom.html.Anchor]
    anchor.href = url
    anchor.download = fileName
    anchor.click()

    // Cleanup
    dom.URL.revokeObjectURL(url)
