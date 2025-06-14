package org.khaki.schoolwatch

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import kotlin.random.Random

data class Task(
    val id: Int = Random.nextInt(),
    val text: String,
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
