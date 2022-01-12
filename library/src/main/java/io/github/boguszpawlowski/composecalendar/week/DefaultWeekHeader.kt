package io.github.boguszpawlowski.composecalendar.week

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import io.github.boguszpawlowski.composecalendar.util.DayOfWeek
import io.github.boguszpawlowski.composecalendar.util.getDisplayName

@Composable
public fun DefaultWeekHeader(
  daysOfWeek: List<DayOfWeek>,
  modifier: Modifier = Modifier,
) {
  Row(modifier = modifier) {
    daysOfWeek.forEach { dayOfWeek ->
      Text(
        textAlign = TextAlign.Center,
        text = dayOfWeek.getDisplayName(),
        modifier = modifier
          .weight(1f)
          .wrapContentHeight()
      )
    }
  }
}

internal fun <T> Array<T>.rotateRight(n: Int): List<T> = takeLast(n) + dropLast(n)
