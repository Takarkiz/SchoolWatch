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
                title = { Text("設定") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "戻る"
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
                    text = "タスク管理",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Task addition section
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("今日のタスクを追加…💖") },
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
                            contentDescription = "タスクを追加",
                            modifier = Modifier.size(36.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display current tasks
                Text(
                    text = "現在のタスク一覧",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (tasks.isEmpty()) {
                    Text(
                        text = "タスクはありません。上記フォームから追加してください。",
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn {
                        items(tasks) { task ->
                            Text(
                                text = "• ${task.text}${if (task.isCompleted) " (完了)" else ""}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // App settings section
                Text(
                    text = "アプリ設定",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Sushi display toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "寿司の表示",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = showSushi,
                        onCheckedChange = onShowSushiChange
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Schedule management section
                Text(
                    text = "スケジュール管理",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Schedule addition section
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = scheduleTitle,
                            onValueChange = { scheduleTitle = it },
                            placeholder = { Text("スケジュールのタイトル") },
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
                                placeholder = { Text("時") },
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
                                placeholder = { Text("分") },
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
                            contentDescription = "スケジュールを追加",
                            modifier = Modifier.size(36.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Display current schedules
                Text(
                    text = "現在のスケジュール一覧",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (schedules.isEmpty()) {
                    Text(
                        text = "スケジュールはありません。上記フォームから追加してください。",
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
            Task(text = "サンプルタスク1"),
            Task(text = "サンプルタスク2", isCompleted = true)
        )
        val previewSchedules = listOf(
            Schedule(title = "朝の会", hours = 8, minutes = 30),
            Schedule(title = "昼休み", hours = 12, minutes = 0)
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
