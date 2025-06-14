package org.khaki.schoolwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.localization.stringResource
import org.khaki.schoolwatch.theme.DraculaTheme

@Composable
fun DigitalClockDisplay(
    hours: String,
    minutes: String,
    seconds: String,
    dateString: String,
    tasks: List<Task>,
    schedules: List<Schedule>,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    onTaskDelete: (Task) -> Unit,
    onSettingsClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Hamburger menu in the top right corner
        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource().settings,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val commonTimeStyle = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = hours,
                    style = commonTimeStyle
                )
                Text(
                    text = ":",
                    style = commonTimeStyle.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = minutes,
                    style = commonTimeStyle
                )
                Text(
                    text = ":",
                    style = commonTimeStyle.copy(fontWeight = FontWeight.Normal),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = seconds,
                    style = commonTimeStyle
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = dateString,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Find the nearest upcoming schedule
            val currentHours = hours.toIntOrNull() ?: 0
            val currentMinutes = minutes.toIntOrNull() ?: 0
            val nearestSchedule = schedules
                .filter { it.isAfterCurrentTime(currentHours, currentMinutes) }
                .minByOrNull {
                    (it.hours * 60 + it.minutes) - (currentHours * 60 + currentMinutes)
                }

            if (nearestSchedule != null) {
                // Calculate time difference in minutes
                val currentTimeInMinutes = currentHours * 60 + currentMinutes
                val scheduleTimeInMinutes = nearestSchedule.hours * 60 + nearestSchedule.minutes
                val minutesUntilSchedule = scheduleTimeInMinutes - currentTimeInMinutes

                if (minutesUntilSchedule < 30) {
                    // Display countdown in red when less than 30 minutes remain
                    Text(
                        text = stringResource().minutesUntilSchedule(nearestSchedule.title, minutesUntilSchedule),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error // Using error color which is typically red
                    )
                } else {
                    // Regular schedule display
                    Text(
                        text = stringResource().nextSchedule(nearestSchedule.getTimeString(), nearestSchedule.title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            TodaysTaskSection(
                tasks = tasks,
                onTaskCheckedChange = onTaskCheckedChange,
                onTaskDelete = onTaskDelete
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDigitalClockDisplay() {
    DraculaTheme {
        val previewTasks = listOf(
            Task(text = stringResource().sampleTask1),
            Task(text = stringResource().sampleTask2, isCompleted = true)
        )
        val previewSchedules = listOf(
            Schedule(title = stringResource().morningMeeting, hours = 8, minutes = 30),
            Schedule(title = stringResource().lunchBreak, hours = 12, minutes = 0)
        )
        DigitalClockDisplay(
            hours = "12",
            minutes = "34",
            seconds = "56",
            dateString = "2023年10月31日 (火)",
            tasks = previewTasks,
            schedules = previewSchedules,
            onTaskCheckedChange = { _, _ -> },
            onTaskDelete = { _ -> },
            onSettingsClick = {}
        )
    }
}
