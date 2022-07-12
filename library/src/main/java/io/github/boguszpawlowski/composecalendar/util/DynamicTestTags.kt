package io.github.boguszpawlowski.composecalendar.util

public object DynamicTestTags {
    public fun getPageTag(index: Int): String = "dynamic-pager-$index"
    public fun getWeekTag(index: Int): String = "week-$index"
}
