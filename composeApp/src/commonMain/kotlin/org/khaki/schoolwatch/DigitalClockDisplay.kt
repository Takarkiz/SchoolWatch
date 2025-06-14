package org.khaki.schoolwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.theme.DraculaTheme

@Composable
fun DigitalClockDisplay(
    hours: String,
    minutes: String,
    seconds: String,
    dateString: String,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally, // 中身を左右中央に！
        verticalArrangement = Arrangement.Center // 中身を上下中央に！
    ) {
        // 時間表示の部分 (これはさっきのRowの中身をそのまま持ってくる感じ！)
        Row(verticalAlignment = Alignment.CenterVertically) {
            val commonTimeStyle = MaterialTheme.typography.displayLarge.copy(
                fontSize = 80.sp, // お好みで調整してね！
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            // (コロンとか秒のスタイルは前回と同じなので省略！)
            Text(
                text = hours,
                style = commonTimeStyle
            )
            Text(
                text = ":",
                style = commonTimeStyle.copy(fontWeight = FontWeight.Normal),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = minutes,
                style = commonTimeStyle
            )
            Text(
                text = ":",
                style = commonTimeStyle.copy(fontWeight = FontWeight.Normal),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = seconds,
                style = commonTimeStyle
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 次の予定が近づいたらここに表示をする
        // 20時までに設定する
        if (hours == "20" && minutes.toInt() <= 29) {
            Text(
                text = "あと${30 - minutes.toInt()}分で終了だよ！",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // 日付表示の部分！💖
        Text(
            text = dateString,
            style = MaterialTheme.typography.headlineMedium, // 時間よりは少し控えめなスタイルで！
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f) // ちょっとだけ色を薄くしてオシャレに！
        )

        Spacer(
            modifier = Modifier.height(64.dp)
        )

        // タスクの一覧

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Today's Task",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Text(
                text = "🎯 TMSの見直し",
                style = TextStyle(
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Text(
                text = "🍣 寿司打に備えよう",
                style = TextStyle(
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

//        Row {
//            for (i in 0..seconds.toInt() - 1) {
//                Text(
//                    text = "🍣",
//                    style = TextStyle(
//                        fontSize = 28.sp
//                    )
//                )
//            }
//        }
    }
}

@Preview
@Composable
private fun PreviewDigitalClockDisplay() {
    DraculaTheme {
        DigitalClockDisplay(
            hours = "12",
            minutes = "34",
            seconds = "56",
            dateString = "2023年10月31日 (火)"
        )
    }
}
