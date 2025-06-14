package org.khaki.schoolwatch

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import kotlin.random.Random

data class Schedule(
    val id: Int = Random.nextInt(),
    val title: String,
    val hours: Int,
    val minutes: Int
) {
    fun getTimeString(): String {
        return "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"
    }

    fun isAfterCurrentTime(currentHours: Int, currentMinutes: Int): Boolean {
        return hours > currentHours || (hours == currentHours && minutes > currentMinutes)
    }
}

internal class PreviewScheduleProvider : PreviewParameterProvider<Schedule> {
    override val values: Sequence<Schedule>
        get() = sequenceOf(
            Schedule(title = "朝の会", hours = 8, minutes = 30),
            Schedule(title = "昼休み", hours = 12, minutes = 0),
            Schedule(title = "下校時間", hours = 15, minutes = 30),
        )
}
