package org.khaki.schoolwatch

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ClockTicker(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default + Job())
) {
    private val _hours = mutableStateOf("00")
    val hours: State<String> = _hours

    private val _minutes = mutableStateOf("00")
    val minutes: State<String> = _minutes

    private val _seconds = mutableStateOf("00")
    val seconds: State<String> = _seconds

    // --- ↓↓↓ 新しく日付用のStateを追加！ ↓↓↓ ---
    private val _currentDate = mutableStateOf("2025年5月24日 (土)") // 初期値は適当でOK！すぐ更新されるから！
    val currentDate: State<String> = _currentDate
    // --- ↑↑↑ ここまで追加！ ↑↑↑ ---

    private var clockJob: Job? = null

    fun start() {
        clockJob?.cancel()
        clockJob = scope.launch {
            while (true) {
                val now: LocalDateTime =
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                _hours.value = now.hour.toString().padStart(2, '0')
                _minutes.value = now.minute.toString().padStart(2, '0')
                _seconds.value = now.second.toString().padStart(2, '0')

                // --- ↓↓↓ ここで日付も取得＆フォーマット！ ↓↓↓ ---
                val year = now.year
                val month = now.monthNumber // .month.value でもOK！ (kotlinx-datetimeのバージョンによるかも)
                val day = now.dayOfMonth
                val dayOfWeekJapanese = when (now.dayOfWeek) {
                    DayOfWeek.MONDAY -> "月"
                    DayOfWeek.TUESDAY -> "火"
                    DayOfWeek.WEDNESDAY -> "水"
                    DayOfWeek.THURSDAY -> "木"
                    DayOfWeek.FRIDAY -> "金"
                    DayOfWeek.SATURDAY -> "土"
                    DayOfWeek.SUNDAY -> "日"
                    else -> ""
                }
                _currentDate.value = "${year}年${month}月${day}日 (${dayOfWeekJapanese})"
                // --- ↑↑↑ 日付取得＆フォーマットここまで！ ↑↑↑ ---

                delay(1000) // 1秒待つ
            }
        }
    }

    fun stop() {
        clockJob?.cancel()
    }
}