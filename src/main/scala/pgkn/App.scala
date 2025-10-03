package pgkn

import com.raquo.laminar.api.L.*

object App:
  def element(): Element =
    div(
      child <-- AppRouter.splitter.signal
    )
