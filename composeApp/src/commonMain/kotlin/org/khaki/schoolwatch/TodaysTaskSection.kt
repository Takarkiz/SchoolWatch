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
fun TodaysTaskSection(modifier: Modifier = Modifier) {
    val tasks = remember { mutableStateListOf<Task>() }
    var textInput by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                modifier = Modifier.weight(1f), placeholder = { Text("今日のタスクを追加…💖") },
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (textInput.isNotBlank()) {
                        tasks.add(Task(text = textInput)) // 新しいタスクを追加！
                        textInput = "" // 入力欄をクリア！
                    }
                },
                enabled = textInput.isNotBlank() // 何か入力されてないと押せないように！
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

        // --- タスク一覧表示 ---
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp) // アイテム間に8dpのスペース！
        ) {
            items(
                items = tasks,
                key = { task -> task.id } // 各アイテムに一意なキーを指定！パフォーマンス向上！
            ) { task ->
                TaskItem(
                    task = task,
                    onCheckedChange = { isChecked ->
                        // チェックボックスの状態が変わったら、taskのisCompletedを更新！
                        // リスト内のオブジェクトのプロパティを直接変更しても、
                        // mutableStateListOf が変更を検知してくれる賢い子！
                        val index = tasks.indexOf(task)
                        if (index != -1) {
                            tasks[index] = task.copy(isCompleted = isChecked)
                        }
                    },
                    onDeleteClick = {
                        tasks.remove(task) // 削除ボタンでリストから削除！
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
        TodaysTaskSection(
            modifier = Modifier.padding(16.dp)
        )
    }
}
