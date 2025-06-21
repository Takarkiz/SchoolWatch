package org.khaki.schoolwatch.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
    shortComment: String,
    onShortCommentChange: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentShortComment by remember { mutableStateOf(shortComment) }

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
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                // Short Comment Section
                Text(
                    text = stringResource().shortComment,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = currentShortComment,
                    onValueChange = { currentShortComment = it },
                    label = { Text(stringResource().shortCommentHint) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onShortCommentChange(currentShortComment) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(stringResource().setShortComment)
                }

                Spacer(modifier = Modifier.height(32.dp))

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
            Task(text = "Sample task 1"),
            Task(text = "Sample task 2", isCompleted = true)
        )
        val previewSchedules = listOf(
            Schedule(title = "Morning Meeting", hours = 8, minutes = 30),
            Schedule(title = "Lunch Break", hours = 12, minutes = 0)
        )
        SettingsScreen(
            tasks = previewTasks,
            onAddTask = { _ -> },
            schedules = previewSchedules,
            onAddSchedule = { _, _, _ -> },
            showSushi = true,
            onShowSushiChange = { _ -> },
            shortComment = "This is a short comment",
            onShortCommentChange = { _ -> },
            onBackClick = {}
        )
    }
}
