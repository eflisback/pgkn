package pgkn.utils

object FormatUtils:
  extension (n: Int)
    /** Pads an integer with leading zeros to the specified width */
    def pad(width: Int = 2): String =
      n.toString.reverse.padTo(width, '0').reverse.mkString
