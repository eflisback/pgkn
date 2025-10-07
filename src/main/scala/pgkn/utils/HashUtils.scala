package pgkn.utils

object HashUtils:
  /** Generates a short, URL-safe hash from a string. Uses a simple hash
    * algorithm and encodes in base36 for compactness. Returns an 8-character
    * hash suitable for URL parameters.
    */
  def shortHash(input: String): String =
    var hash = 0
    input.foreach(char =>
      hash = ((hash << 5) - hash) + char.toInt
      hash = hash & hash
    )
    val absHash = BigInt(math.abs(hash))
    absHash.toString(36).take(8).padTo(8, '0')
