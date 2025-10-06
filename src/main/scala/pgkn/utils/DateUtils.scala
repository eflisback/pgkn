package pgkn.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.WeekFields

object DateUtils:
  def getWeekNumber(timestamp: BigInt): Int =
    val instant = Instant.ofEpochMilli(timestamp.toLong)
    val localDate = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate

    val week = localDate.get(WeekFields.ISO.weekOfYear)

    if week == 0 then localDate.minusWeeks(1).get(WeekFields.ISO.weekOfYear)
    else week

  def getSwedishDayName(timestamp: BigInt): String =
    val instant = Instant.ofEpochMilli(timestamp.toLong)
    val localDate = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate
    val dayOfWeek = localDate.getDayOfWeek.getValue

    dayOfWeek match
      case 1 => "mån"
      case 2 => "tis"
      case 3 => "ons"
      case 4 => "tor"
      case 5 => "fre"
      case 6 => "lör"
      case 7 => "sön"
      case _ => ""
