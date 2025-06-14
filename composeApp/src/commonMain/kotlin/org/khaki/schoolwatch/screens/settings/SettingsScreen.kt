package org.khaki.schoolwatch.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.Schedule
import org.khaki.schoolwatch.Task
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
                // App Settings Section
                AppSettingsSection(
                    showSushi = showSushi,
                    onShowSushiChange = onShowSushiChange,
                    language = language,
                    onLanguageChange = onLanguageChange
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Task Management Section
                TaskManagementSection(
                    tasks = tasks,
                    onAddTask = onAddTask
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Schedule Management Section
                ScheduleManagementSection(
                    schedules = schedules,
                    onAddSchedule = onAddSchedule
                )
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