package org.khaki.schoolwatch

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.theme.DraculaTheme

@Composable
fun DigitalClockDisplay(
    hours: String,
    minutes: String,
    seconds: String,
    dateString: String,
    tasks: List<Task>,
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
                contentDescription = "設定",
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

            if (hours == "20" && minutes.toInt() <= 29) {
                Text(
                    text = "あと${30 - minutes.toInt()}分で終了だよ！",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = dateString,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )

            Spacer(
                modifier = Modifier.height(64.dp)
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
            Task(text = "サンプルタスク1"),
            Task(text = "サンプルタスク2", isCompleted = true)
        )
        DigitalClockDisplay(
            hours = "12",
            minutes = "34",
            seconds = "56",
            dateString = "2023年10月31日 (火)",
            tasks = previewTasks,
            onTaskCheckedChange = { _, _ -> },
            onTaskDelete = { _ -> },
            onSettingsClick = {}
        )
    }
}
