package org.khaki.schoolwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.theme.DraculaTheme

@Composable
@Preview
fun App() {
    DraculaTheme {
        val clockTicker = remember { ClockTicker() }

        // Composableが初めて画面に表示された時にクロックを開始する
        LaunchedEffect(Unit) { // key1 = Unit なので最初の一回だけ実行
            clockTicker.start()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                )
                .safeContentPadding(),
            contentAlignment = Alignment.Center
        ) {
            SushiSecondHandClock()

            DigitalClockDisplay(
                hours = clockTicker.hours.value,
                minutes = clockTicker.minutes.value,
                seconds = clockTicker.seconds.value,
                dateString = clockTicker.currentDate.value
            )
        }
    }
}