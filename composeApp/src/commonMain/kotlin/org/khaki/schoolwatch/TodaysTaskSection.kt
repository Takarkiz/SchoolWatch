package org.khaki.schoolwatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
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
                text = "Today's Task",
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

@Preview
@Composable
private fun PreviewTodaysTaskSection() {
    DraculaTheme {
        val previewTasks = listOf(
            Task(text = "サンプルタスク1"),
            Task(text = "サンプルタスク2", isCompleted = true)
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
