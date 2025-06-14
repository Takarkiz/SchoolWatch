package org.khaki.schoolwatch

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.theme.DraculaTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun SushiSecondHandClock(
    clockTicker: ClockTicker? = null,
    modifier: Modifier = Modifier
) {
    // Get current seconds and milliseconds or use default values if clockTicker is null
    val seconds = clockTicker?.seconds?.value?.toIntOrNull() ?: 0
    val milliseconds = clockTicker?.milliseconds?.value?.toIntOrNull() ?: 0

    // Calculate precise time value with milliseconds for smooth movement
    val secondsWithMillis = seconds + (milliseconds / 1000f)

    // Calculate angle in radians based on current seconds (0-59.999)
    // For a clock, 0 seconds is at 12 o'clock (top), and we move clockwise
    // We subtract from 90 degrees (π/2) to start at the top and then negate to go clockwise
    // Full circle is 2π radians, so we multiply by 2π/60 to complete one circle in 60 seconds
    val angleInRadians = -((PI.toFloat() / 2) - (secondsWithMillis * (2 * PI.toFloat() / 60)))

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val boxWidthPx = constraints.maxWidth
        val boxHeightPx = constraints.maxHeight

        // Fixed size for the sushi emoji (in pixels)
        val sushiSize = 60f

        // Calculate the center of the box
        val centerX = boxWidthPx / 2f
        val centerY = boxHeightPx / 2f

        // Calculate the radius of the clock (slightly smaller than the box to keep sushi within bounds)
        // We use the smaller dimension and subtract the sushi size to ensure it stays within bounds
        val radius = (minOf(boxWidthPx, boxHeightPx) / 2f) - sushiSize

        // Calculate position using trigonometric functions
        // cos gives x-coordinate, sin gives y-coordinate
        val x = centerX + radius * cos(angleInRadians)
        val y = centerY + radius * sin(angleInRadians)

        Text(
            text = "🍣",
            fontSize = 60.sp,
            modifier = Modifier.offset {
                IntOffset(
                    (x - sushiSize / 2).roundToInt(),
                    (y - sushiSize / 2).roundToInt()
                )
            }
        )
    }
}

@Preview
@Composable
private fun PreviewSushiSecondHandClock() {
    DraculaTheme {
        SushiSecondHandClock()
    }
}
