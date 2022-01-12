package io.github.boguszpawlowski.composecalendar.week

import io.github.boguszpawlowski.composecalendar.day.WeekDay
import io.github.boguszpawlowski.composecalendar.util.DayOfWeek
import io.github.boguszpawlowski.composecalendar.util.daysUntil
import io.github.boguszpawlowski.composecalendar.util.lengthOfMonth
import io.github.boguszpawlowski.composecalendar.util.toDayOfWeek
import org.joda.time.LocalDate
import org.joda.time.YearMonth

private const val DaysInAWeek = 7

internal fun YearMonth.getWeeks(
  includeAdjacentMonths: Boolean,
  firstDayOfTheWeek: DayOfWeek,
  today: LocalDate = LocalDate.now(),
): List<Week> {
  val daysLength = lengthOfMonth()

  val starOffset = toLocalDate(1).dayOfWeek.toDayOfWeek() daysUntil firstDayOfTheWeek
  val endOffset =
    DaysInAWeek - (toLocalDate(daysLength).dayOfWeek.toDayOfWeek() daysUntil firstDayOfTheWeek) - 1

  return (1 - starOffset..daysLength + endOffset).chunked(DaysInAWeek).mapIndexed { index, days ->
    Week(
      isFirstWeekOfTheMonth = index == 0,
      days = days.mapNotNull { dayOfMonth ->
        val (date, isFromCurrentMonth) = when (dayOfMonth) {
          in Int.MIN_VALUE..0 -> if (includeAdjacentMonths) {
            val previousMonth = this.minusMonths(1)
            previousMonth.toLocalDate(previousMonth.lengthOfMonth() + dayOfMonth) to false
          } else {
            return@mapNotNull null
          }
          in 1..daysLength -> toLocalDate(dayOfMonth) to true
          else -> if (includeAdjacentMonths) {
            val previousMonth = this.plusMonths(1)
            previousMonth.toLocalDate(dayOfMonth - daysLength) to false
          } else {
            return@mapNotNull null
          }
        }

        WeekDay(
          date = date,
          isFromCurrentMonth = isFromCurrentMonth,
          isCurrentDay = date.equals(today),
        )
      }
    )
  }
}
