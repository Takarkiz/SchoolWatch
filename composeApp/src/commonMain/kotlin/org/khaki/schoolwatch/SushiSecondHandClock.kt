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
    // ステップ１：無限ループするアニメーションの定義！
    val infiniteTransition = rememberInfiniteTransition(label = "sushi_transition")

    // 0fから4fまでの値を60秒かけて変化させるアニメーション！
    // 0f-1f: 上の辺, 1f-2f: 右の辺, 2f-3f: 下の辺, 3f-4f: 左の辺 って感じ！
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 4f, // 4辺だから4fまで！
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 60000, easing = LinearEasing), // 60秒！
            repeatMode = RepeatMode.Restart // 1周終わったら最初から！
        ),
        label = "sushi_progress"
    )

    // ステップ２＆３：画面サイズを取得して、お寿司の位置を計算！
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        // maxWidth と maxHeight で、このBoxの幅と高さが取れる！
        val boxWidthPx = constraints.maxWidth
        val boxHeightPx = constraints.maxHeight

        // プログレス(0f-4f)に応じて、お寿司の(x, y)座標を計算する！
        // ここが一番頭使うとこかも！🤔
        val (x, y) = when {
            // 上の辺 (左 -> 右)
            progress <= 1.0f -> {
                val currentX = boxWidthPx * progress
                Pair(currentX, 0f)
            }
            // 右の辺 (上 -> 下)
            progress <= 2.0f -> {
                val currentY = boxHeightPx * (progress - 1.0f)
                Pair(boxWidthPx.toFloat(), currentY)
            }
            // 下の辺 (右 -> 左)
            progress <= 3.0f -> {
                val currentX = boxWidthPx * (1.0f - (progress - 2.0f))
                Pair(currentX, boxHeightPx.toFloat())
            }
            // 左の辺 (下 -> 上)
            else -> {
                val currentY = boxHeightPx * (1.0f - (progress - 3.0f))
                Pair(0f, currentY)
            }
        }

        // ステップ４：計算した位置にお寿司アイコンを配置！
        Text(
            text = "🍣", // お寿司アイコン！ ImageComposableでもOK！
            fontSize = 60.sp, // アイコンのサイズはお好みで！
            modifier = Modifier.offset {
                IntOffset(
                    x.roundToInt() - 60 / 2,
                    y.roundToInt() - 60 / 2
                )
            }
            // (おまけ) お寿司アイコンの真ん中がフチに来るように、
            // もっと厳密にやるなら、アイコンの半分のサイズをx, yから引くと完璧！
            // .offset { IntOffset(x.roundToInt() - iconSizePx / 2, y.roundToInt() - iconSizePx / 2) }
            // ↑これをやるには、アイコンのサイズを先に測る工夫がいるから、まずはこれでOK！
        )
    }
}