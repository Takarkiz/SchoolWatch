package org.khaki.schoolwatch.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

/**
 * Enum representing the supported languages
 */
enum class Language {
    JAPANESE,
    ENGLISH
}

/**
 * Interface for string resources
 */
interface StringResources {
    // Common
    val back: String
    val deleteTask: String

    // Settings Screen
    val settings: String
    val taskManagement: String
    val addTaskPlaceholder: String
    val addTask: String
    val currentTasks: String
    val noTasks: String
    val taskCompleted: String
    val appSettings: String
    val showSushi: String
    val scheduleManagement: String
    val scheduleTitlePlaceholder: String
    val hours: String
    val minutes: String
    val addSchedule: String
    val currentSchedules: String
    val noSchedules: String

    // Digital Clock Display
    fun nextSchedule(time: String, title: String): String
    fun minutesUntilSchedule(title: String, minutes: Int): String

    // Today's Task Section
    val todaysTask: String

    // Preview Data
    val sampleTask1: String
    val sampleTask2: String
    val morningMeeting: String
    val lunchBreak: String
    val schoolDismissal: String

    // Days of Week
    val monday: String
    val tuesday: String
    val wednesday: String
    val thursday: String
    val friday: String
    val saturday: String
    val sunday: String

    // Date Format
    fun dateFormat(year: Int, month: Int, day: Int, dayOfWeek: String): String
}

/**
 * Japanese string resources
 */
class JapaneseStringResources : StringResources {
    // Common
    override val back = "戻る"
    override val deleteTask = "タスクを削除"

    // Settings Screen
    override val settings = "設定"
    override val taskManagement = "タスク管理"
    override val addTaskPlaceholder = "今日のタスクを追加…💖"
    override val addTask = "タスクを追加"
    override val currentTasks = "現在のタスク一覧"
    override val noTasks = "タスクはありません。上記フォームから追加してください。"
    override val taskCompleted = "完了"
    override val appSettings = "アプリ設定"
    override val showSushi = "寿司の表示"
    override val scheduleManagement = "スケジュール管理"
    override val scheduleTitlePlaceholder = "スケジュールのタイトル"
    override val hours = "時"
    override val minutes = "分"
    override val addSchedule = "スケジュールを追加"
    override val currentSchedules = "現在のスケジュール一覧"
    override val noSchedules = "スケジュールはありません。上記フォームから追加してください。"

    // Digital Clock Display
    override fun nextSchedule(time: String, title: String) = "次の予定: $time $title"
    override fun minutesUntilSchedule(title: String, minutes: Int) = "${title}まで${minutes}分だよ"

    // Today's Task Section
    override val todaysTask = "Today's Task"

    // Preview Data
    override val sampleTask1 = "サンプルタスク1"
    override val sampleTask2 = "サンプルタスク2"
    override val morningMeeting = "朝の会"
    override val lunchBreak = "昼休み"
    override val schoolDismissal = "下校時間"

    // Days of Week
    override val monday = "月"
    override val tuesday = "火"
    override val wednesday = "水"
    override val thursday = "木"
    override val friday = "金"
    override val saturday = "土"
    override val sunday = "日"

    // Date Format
    override fun dateFormat(year: Int, month: Int, day: Int, dayOfWeek: String) =
        "${year}年${month}月${day}日 (${dayOfWeek})"
}

/**
 * English string resources
 */
class EnglishStringResources : StringResources {
    // Common
    override val back = "Back"
    override val deleteTask = "Delete task"

    // Settings Screen
    override val settings = "Settings"
    override val taskManagement = "Task Management"
    override val addTaskPlaceholder = "Add today's task…💖"
    override val addTask = "Add task"
    override val currentTasks = "Current Tasks"
    override val noTasks = "No tasks. Please add from the form above."
    override val taskCompleted = "Completed"
    override val appSettings = "App Settings"
    override val showSushi = "Show Sushi"
    override val scheduleManagement = "Schedule Management"
    override val scheduleTitlePlaceholder = "Schedule title"
    override val hours = "Hour"
    override val minutes = "Minute"
    override val addSchedule = "Add schedule"
    override val currentSchedules = "Current Schedules"
    override val noSchedules = "No schedules. Please add from the form above."

    // Digital Clock Display
    override fun nextSchedule(time: String, title: String) = "Next schedule: $time $title"
    override fun minutesUntilSchedule(title: String, minutes: Int) = "$title in $minutes minutes"

    // Today's Task Section
    override val todaysTask = "Today's Task"

    // Preview Data
    override val sampleTask1 = "Sample Task 1"
    override val sampleTask2 = "Sample Task 2"
    override val morningMeeting = "Morning Meeting"
    override val lunchBreak = "Lunch Break"
    override val schoolDismissal = "School Dismissal"

    // Days of Week
    override val monday = "Mon"
    override val tuesday = "Tue"
    override val wednesday = "Wed"
    override val thursday = "Thu"
    override val friday = "Fri"
    override val saturday = "Sat"
    override val sunday = "Sun"

    // Date Format
    override fun dateFormat(year: Int, month: Int, day: Int, dayOfWeek: String) =
        "$month/$day/$year ($dayOfWeek)"
}

/**
 * Composition local for string resources
 */
val LocalStringResources = compositionLocalOf<StringResources> { JapaneseStringResources() }

/**
 * Provides string resources based on the given language
 */
@Composable
fun ProvideStringResources(language: Language, content: @Composable () -> Unit) {
    val stringResources = remember(language) {
        when (language) {
            Language.JAPANESE -> JapaneseStringResources()
            Language.ENGLISH -> EnglishStringResources()
        }
    }

    CompositionLocalProvider(LocalStringResources provides stringResources) {
        content()
    }
}

/**
 * Access string resources in a composable function
 */
@Composable
fun stringResource(): StringResources {
    return LocalStringResources.current
}