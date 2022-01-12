package io.github.boguszpawlowski.composecalendar.util

import org.joda.time.LocalDate
import org.joda.time.YearMonth

internal fun Collection<LocalDate>.addOrRemoveIfExists(date: LocalDate) =
  if (contains(date)) {
    this - date
  } else {
    this + date
  }

public enum class DayOfWeek(public val value: Int) {
  MONDAY(1),
  TUESDAY(2),
  WEDNESDAY(3),
  THURSDAY(4),
  FRIDAY(5),
  SATURDAY(6),
  SUNDAY(7),
}

public fun Int.toDayOfWeek(): DayOfWeek = DayOfWeek.values().find { it.value == this }!!

public infix fun DayOfWeek.daysUntil(other: DayOfWeek): Int = (7 + (value - other.value)) % 7

public fun DayOfWeek.getDisplayName(): String = LocalDate(2022, 1, 9).plusDays(value).toString("E")

internal fun YearMonth.lengthOfMonth(): Int = toLocalDate(1).dayOfMonth().maximumValue
internal fun YearMonth.atEndOfMonth(): LocalDate = toLocalDate(lengthOfMonth())
internal fun YearMonth.inc(): YearMonth = plusMonths(1)
internal fun YearMonth.dec(): YearMonth = minusMonths(1)

internal fun LocalDate.toEpochDay(): Long = toDateTimeAtStartOfDay().millis / 86_400_000

internal val Int.ordinal: Int get() = this - 1

public fun YearMonth.monthName(): String = toLocalDate(1).toString("MMMM")
