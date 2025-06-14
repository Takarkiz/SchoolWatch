package org.khaki.schoolwatch.screens.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.khaki.schoolwatch.ClockTicker
import org.khaki.schoolwatch.DigitalClockDisplay
import org.khaki.schoolwatch.Schedule
import org.khaki.schoolwatch.SushiSecondHandClock
import org.khaki.schoolwatch.Task

@Composable
fun ClockScreen(
    clockTicker: ClockTicker,
    tasks: List<Task>,
    schedules: List<Schedule>,
    showSushi: Boolean,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    onTaskDelete: (Task) -> Unit,
    onSettingsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .safeContentPadding(),
        contentAlignment = Alignment.Center
    ) {
        if (showSushi) {
            SushiSecondHandClock()
        }

        DigitalClockDisplay(
            hours = clockTicker.hours.value,
            minutes = clockTicker.minutes.value,
            seconds = clockTicker.seconds.value,
            dateString = clockTicker.currentDate.value,
            tasks = tasks,
            schedules = schedules,
            onTaskCheckedChange = onTaskCheckedChange,
            onTaskDelete = onTaskDelete,
            onSettingsClick = onSettingsClick
        )
    }
}