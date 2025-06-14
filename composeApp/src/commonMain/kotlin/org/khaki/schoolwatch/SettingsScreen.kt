package org.khaki.schoolwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.localization.Language
import org.khaki.schoolwatch.localization.stringResource
import org.khaki.schoolwatch.theme.DraculaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    tasks: List<Task>,
    onAddTask: (String) -> Unit,
    schedules: List<Schedule>,
    onAddSchedule: (String, Int, Int) -> Unit,
    showSushi: Boolean,
    onShowSushiChange: (Boolean) -> Unit,
    language: Language = Language.JAPANESE,
    onLanguageChange: (Language) -> Unit = {},
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var textInput by remember { mutableStateOf("") }
    var scheduleTitle by remember { mutableStateOf("") }
    var scheduleHours by remember { mutableStateOf("") }
    var scheduleMinutes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource().settings) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource().back
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource().taskManagement,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Task addition section
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text(stringResource().addTaskPlaceholder) },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (textInput.isNotBlank()) {
                                onAddTask(textInput)
                                textInput = ""
                            }
                        },
                        enabled = textInput.isNotBlank()
                    ) {
                        Icon(
                            Icons.Filled.AddCircle,
                            contentDescription = stringResource().addTask,
                            modifier = Modifier.size(36.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display current tasks
                Text(
                    text = stringResource().currentTasks,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (tasks.isEmpty()) {
                    Text(
                        text = stringResource().noTasks,
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn {
                        items(
                            items = tasks,
                            key = { task -> task.id }
                        ) { task ->
                            Text(
                                text = "• ${task.text}${if (task.isCompleted) " (${stringResource().taskCompleted})" else ""}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // App settings section
                Text(
                    text = stringResource().appSettings,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sushi display toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource().showSushi,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = showSushi,
                        onCheckedChange = onShowSushiChange
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Language selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = if (language == Language.JAPANESE) "言語" else "Language",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "日本語",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (language == Language.JAPANESE)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Switch(
                            checked = language == Language.ENGLISH,
                            onCheckedChange = { isEnglish ->
                                onLanguageChange(if (isEnglish) Language.ENGLISH else Language.JAPANESE)
                            }
                        )
                        Text(
                            text = "English",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (language == Language.ENGLISH)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Schedule management section
                Text(
                    text = stringResource().scheduleManagement,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

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
private fun PreviewSettingsScreen() {
    DraculaTheme {
        val previewTasks = listOf(
            Task(text = stringResource().sampleTask1),
            Task(text = stringResource().sampleTask2, isCompleted = true)
        )
        val previewSchedules = listOf(
            Schedule(title = stringResource().morningMeeting, hours = 8, minutes = 30),
            Schedule(title = stringResource().lunchBreak, hours = 12, minutes = 0)
        )
        SettingsScreen(
            tasks = previewTasks,
            onAddTask = { _ -> },
            schedules = previewSchedules,
            onAddSchedule = { _, _, _ -> },
            showSushi = true,
            onShowSushiChange = { _ -> },
            onBackClick = {}
        )
    }
}
