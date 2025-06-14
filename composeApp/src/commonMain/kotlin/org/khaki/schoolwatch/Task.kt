package org.khaki.schoolwatch

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import kotlin.random.Random

// UUID的なものがあると、もっとしっかり管理できるけど、まずはシンプルに！
// ランダムなIDを振ることで、リスト更新時のキーとして使える！
data class Task(
    val id: Int = Random.nextInt(), // 一意なIDを適当に作る！
    val text: String,
    // isCompletedは変更されるから、State<Boolean>じゃなくてvarで持つとシンプル！
    // ComposeのリストのState (mutableStateListOf) が更新を検知してくれる！
    var isCompleted: Boolean = false
)

internal class PreviewTaskProvider : PreviewParameterProvider<Task> {
    override val values: Sequence<Task>
        get() = sequenceOf(
            Task(text = "Task 1"),
            Task(text = "Task 2"),
            Task(text = "Task 3"),
        )

}