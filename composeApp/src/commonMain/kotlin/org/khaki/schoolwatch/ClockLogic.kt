package org.khaki.schoolwatch

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.khaki.schoolwatch.localization.JapaneseStringResources
import org.khaki.schoolwatch.localization.StringResources

class ClockTicker(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + Job()),
    private var stringResources: StringResources = JapaneseStringResources()
) {
    private val _hours = mutableStateOf("00")
    val hours: State<String> = _hours

    private val _minutes = mutableStateOf("00")
    val minutes: State<String> = _minutes

    private val _seconds = mutableStateOf("00")
    val seconds: State<String> = _seconds

    private val _currentDate = mutableStateOf("2025年5月24日 (土)")
    val currentDate: State<String> = _currentDate

    private var clockJob: Job? = null

    fun start() {
        clockJob?.cancel()
        clockJob = scope.launch {
            while (true) {
                val now: LocalDateTime =
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                _hours.value = now.hour.toString().padStart(2, '0')
                _minutes.value = now.minute.toString().padStart(2, '0')
                _seconds.value = now.second.toString().padStart(2, '0')

                val year = now.year
                val month = now.monthNumber
                val day = now.dayOfMonth
                val dayOfWeekString = when (now.dayOfWeek) {
                    DayOfWeek.MONDAY -> stringResources.monday
                    DayOfWeek.TUESDAY -> stringResources.tuesday
                    DayOfWeek.WEDNESDAY -> stringResources.wednesday
                    DayOfWeek.THURSDAY -> stringResources.thursday
                    DayOfWeek.FRIDAY -> stringResources.friday
                    DayOfWeek.SATURDAY -> stringResources.saturday
                    DayOfWeek.SUNDAY -> stringResources.sunday
                    else -> ""
                }
                _currentDate.value = stringResources.dateFormat(year, month, day, dayOfWeekString)

                delay(1000)
            }
        }
    }

    fun stop() {
        clockJob?.cancel()
    }

    fun updateStringResources(newStringResources: StringResources) {
        stringResources = newStringResources
    }
}
