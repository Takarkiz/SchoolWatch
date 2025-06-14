package org.khaki.schoolwatch.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.Schedule
import org.khaki.schoolwatch.localization.stringResource
import org.khaki.schoolwatch.theme.DraculaColors
import org.khaki.schoolwatch.theme.DraculaTheme

@Composable
fun ScheduleManagementSection(
    schedules: List<Schedule>,
    onAddSchedule: (String, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var scheduleTitle by remember { mutableStateOf("") }
    var scheduleHours by remember { mutableStateOf("") }
    var scheduleMinutes by remember { mutableStateOf("") }

    Column(modifier = modifier) {
        // Schedule management section
        Text(
            text = stringResource().scheduleManagement,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Schedule Management Section with background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = DraculaColors.Purple.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                // Schedule addition section
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = scheduleTitle,
                            onValueChange = { scheduleTitle = it },
                            placeholder = { Text(stringResource().scheduleTitlePlaceholder) },
                            singleLine = true,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row {
                            OutlinedTextField(
                                value = scheduleHours,
                                onValueChange = {
                                    if (it.isEmpty() || (it.toIntOrNull() != null && it.toInt() in 0..23)) {
                                        scheduleHours = it
                                    }
                                },
                                placeholder = { Text(stringResource().hours) },
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(
                                value = scheduleMinutes,
                                onValueChange = {
                                    if (it.isEmpty() || (it.toIntOrNull() != null && it.toInt() in 0..59)) {
                                        scheduleMinutes = it
                                    }
                                },
                                placeholder = { Text(stringResource().minutes) },
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            val hours = scheduleHours.toIntOrNull()
                            val minutes = scheduleMinutes.toIntOrNull()
                            if (scheduleTitle.isNotBlank() && hours != null && minutes != null) {
                                onAddSchedule(scheduleTitle, hours, minutes)
                                scheduleTitle = ""
                                scheduleHours = ""
                                scheduleMinutes = ""
                            }
                        },
                        enabled = scheduleTitle.isNotBlank() &&
                                scheduleHours.isNotBlank() && scheduleHours.toIntOrNull() != null &&
                                scheduleMinutes.isNotBlank() && scheduleMinutes.toIntOrNull() != null
                    ) {
                        Icon(
                            Icons.Filled.AddCircle,
                            contentDescription = stringResource().addSchedule,
                            modifier = Modifier.size(36.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display current schedules
                Text(
                    text = stringResource().currentSchedules,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (schedules.isEmpty()) {
                    Text(
                        text = stringResource().noSchedules,
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn {
                        items(schedules) { schedule ->
                            Text(
                                text = "• ${schedule.getTimeString()} ${schedule.title}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewScheduleManagementSection() {
    DraculaTheme {
        val previewSchedules = listOf(
            Schedule(title = "Morning Meeting", hours = 8, minutes = 30),
            Schedule(title = "Lunch Break", hours = 12, minutes = 0)
        )
        ScheduleManagementSection(
            schedules = previewSchedules,
            onAddSchedule = { _, _, _ -> }
        )
    }
}
