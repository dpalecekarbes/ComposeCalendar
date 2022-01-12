package io.github.boguszpawlowski.composecalendar.selection

import io.github.boguszpawlowski.composecalendar.util.toEpochDay
import org.joda.time.LocalDate

internal fun Collection<LocalDate>.startOrMax() = firstOrNull() ?: LocalDate(292278994, 12, 31)

internal fun Collection<LocalDate>.endOrNull() = drop(1).lastOrNull()

internal fun Collection<LocalDate>.fillUpTo(date: LocalDate) =
  (0..date.toEpochDay() - first().toEpochDay()).map {
    first().plusDays(it.toInt())
  }
