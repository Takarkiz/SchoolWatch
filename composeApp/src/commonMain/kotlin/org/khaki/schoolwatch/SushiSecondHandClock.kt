package org.khaki.schoolwatch

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun SushiSecondHandClock(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "sushi_transition")

    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sushi_progress"
    )

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val boxWidthPx = constraints.maxWidth
        val boxHeightPx = constraints.maxHeight

        val (x, y) = when {
            progress <= 1.0f -> {
                val currentX = boxWidthPx * progress
                Pair(currentX, 0f)
            }
            progress <= 2.0f -> {
                val currentY = boxHeightPx * (progress - 1.0f)
                Pair(boxWidthPx.toFloat(), currentY)
            }
            progress <= 3.0f -> {
                val currentX = boxWidthPx * (1.0f - (progress - 2.0f))
                Pair(currentX, boxHeightPx.toFloat())
            }
            else -> {
                val currentY = boxHeightPx * (1.0f - (progress - 3.0f))
                Pair(0f, currentY)
            }
        }

        Text(
            text = "🍣",
            fontSize = 60.sp,
            modifier = Modifier.offset {
                IntOffset(
                    x.roundToInt() - 60 / 2,
                    y.roundToInt() - 60 / 2
                )
            }
        )
    }
}
