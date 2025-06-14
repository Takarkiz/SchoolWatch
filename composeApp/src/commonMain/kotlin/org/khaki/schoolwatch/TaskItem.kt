package org.khaki.schoolwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.localization.stringResource
import org.khaki.schoolwatch.theme.DraculaTheme

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = task.text,
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
            color = if (task.isCompleted) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurface
        )
        IconButton(onClick = onDeleteClick) {
            Icon(
                Icons.Default.Delete,
                contentDescription = stringResource().deleteTask,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Preview()
@Composable
private fun PreviewTaskItem() {
    DraculaTheme {
        TaskItem(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface
                ),
            task = Task(
                id = 1,
                text = stringResource().sampleTask1,
                isCompleted = false
            ),
            onCheckedChange = {},
            onDeleteClick = {}
        )
    }
}
