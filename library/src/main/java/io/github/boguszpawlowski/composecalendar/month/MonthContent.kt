package io.github.boguszpawlowski.composecalendar.month

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import io.github.boguszpawlowski.composecalendar.util.DayOfWeek
import io.github.boguszpawlowski.composecalendar.util.DynamicTestTags
import io.github.boguszpawlowski.composecalendar.week.WeekContent
import io.github.boguszpawlowski.composecalendar.week.getWeeks
import org.joda.time.LocalDate
import org.joda.time.YearMonth

internal const val DaysOfWeek = 7

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun <T : SelectionState> MonthPager(
  showAdjacentMonths: Boolean,
  selectionState: T,
  monthState: MonthState,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {

  val pagerState = rememberPagerState(initialPage = StartIndex)
  val coroutineScope = rememberCoroutineScope()

  val monthPagerState = remember {
    MonthPagerState(
      coroutineScope = coroutineScope,
      monthState = monthState,
      pagerState = pagerState,
    )
  }

  HorizontalPager(
    count = LastPage,
    modifier = modifier.testTag("MonthPager"),
    state = pagerState,
    verticalAlignment = Alignment.Top,
  ) { index ->
    MonthContent(
      showAdjacentMonths = showAdjacentMonths,
      selectionState = selectionState,
      currentMonth = monthPagerState.getMonthForIndex(index.toIndex()),
      today = today,
      daysOfWeek = daysOfWeek,
      dayContent = dayContent,
      weekHeader = weekHeader,
      monthContainer = monthContainer,
      testTag = DynamicTestTags.getPageTag(index.toIndex()),
    )
  }
}

private const val LastPage = 20_000
private const val StartIndex = LastPage / 2

internal fun Int.toIndex(startIndex: Int = StartIndex, pageCount: Int = PageCount) =
  ((this - startIndex).floorMod(pageCount) + 1).mod(pageCount)

private fun Int.floorMod(other: Int): Int = when (other) {
  0 -> this
  else -> this - floorDiv(other) * other
}

@Composable
internal fun <T : SelectionState> MonthContent(
  showAdjacentMonths: Boolean,
  selectionState: T,
  currentMonth: YearMonth,
  daysOfWeek: List<DayOfWeek>,
  today: LocalDate,
  modifier: Modifier = Modifier,
  testTag: String? = null,
  dayContent: @Composable BoxScope.(DayState<T>) -> Unit,
  weekHeader: @Composable BoxScope.(List<DayOfWeek>) -> Unit,
  monthContainer: @Composable (content: @Composable (PaddingValues) -> Unit) -> Unit,
) {
  Column(
    modifier = testTag?.let { Modifier.testTag(it) } ?: Modifier
  ) {
    Box(
      modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight(),
      content = { weekHeader(daysOfWeek) },
    )

    monthContainer { paddingValues ->
      Column(
        modifier = Modifier
          .wrapContentWidth()
          .padding(paddingValues)
      ) {
        currentMonth.getWeeks(
          includeAdjacentMonths = showAdjacentMonths,
          firstDayOfTheWeek = daysOfWeek.first(),
          today = today,
        ).forEachIndexed { index, week ->
          WeekContent(
            week = week,
            selectionState = selectionState,
            dayContent = dayContent,
            modifier = Modifier.testTag(DynamicTestTags.getWeekTag(index))
          )
        }
      }
    }
  }
}
