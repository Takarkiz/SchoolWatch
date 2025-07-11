package org.khaki.schoolwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.localization.stringResource
import org.khaki.schoolwatch.theme.DraculaTheme

@Composable
fun TodaysTaskSection(
    tasks: List<Task>,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    onTaskDelete: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        if (tasks.isNotEmpty()) {
            Text(
                text = stringResource().todaysTask,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = tasks,
                key = { task -> task.id }
            ) { task ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 600.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TaskItem(
                        task = task,
                        onCheckedChange = { isChecked ->
                            onTaskCheckedChange(task, isChecked)
                        },
                        onDeleteClick = {
                            onTaskDelete(task)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTodaysTaskSection() {
    DraculaTheme {
        val previewTasks = listOf(
            Task(text = stringResource().sampleTask1),
            Task(text = stringResource().sampleTask2, isCompleted = true)
        )
        TodaysTaskSection(
            tasks = previewTasks,
            onTaskCheckedChange = { _, _ -> },
            onTaskDelete = { _ -> },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewEmptyTodaysTaskSection() {
    DraculaTheme {
        TodaysTaskSection(
            tasks = emptyList(),
            onTaskCheckedChange = { _, _ -> },
            onTaskDelete = { _ -> },
            modifier = Modifier.padding(16.dp)
        )
    }
}
