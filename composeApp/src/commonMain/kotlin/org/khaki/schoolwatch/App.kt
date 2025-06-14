package org.khaki.schoolwatch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.khaki.schoolwatch.localization.EnglishStringResources
import org.khaki.schoolwatch.localization.JapaneseStringResources
import org.khaki.schoolwatch.localization.Language
import org.khaki.schoolwatch.localization.ProvideStringResources
import org.khaki.schoolwatch.theme.DraculaTheme

enum class Screen {
    CLOCK,
    SETTINGS
}

@Composable
@Preview
fun App() {
    DraculaTheme {
        val clockTicker = remember { ClockTicker() }
        var currentScreen by remember { mutableStateOf(Screen.CLOCK) }
        val tasks = remember { mutableStateListOf<Task>() }
        val schedules = remember { mutableStateListOf<Schedule>() }
        var showSushi by remember { mutableStateOf(false) }
        var language by remember { mutableStateOf(Language.JAPANESE) }
        val stringResources = remember(language) {
            when (language) {
                Language.JAPANESE -> JapaneseStringResources()
                Language.ENGLISH -> EnglishStringResources()
            }
        }

        LaunchedEffect(Unit) {
            clockTicker.start()
        }

        DisposableEffect(Unit) {
            onDispose {
                clockTicker.stop()
            }
        }

        LaunchedEffect(stringResources) {
            clockTicker.updateStringResources(stringResources)
        }

        ProvideStringResources(language = language) {
            when (currentScreen) {
                Screen.CLOCK -> {
                    ClockScreen(
                        clockTicker = clockTicker,
                        tasks = tasks,
                        schedules = schedules,
                        showSushi = showSushi,
                        onTaskCheckedChange = { task, isChecked ->
                            val index = tasks.indexOf(task)
                            if (index != -1) {
                                tasks[index] = task.copy(isCompleted = isChecked)
                            }
                        },
                        onTaskDelete = { task ->
                            tasks.remove(task)
                        },
                        onSettingsClick = { currentScreen = Screen.SETTINGS }
                    )
                }

                Screen.SETTINGS -> {
                    SettingsScreen(
                        tasks = tasks,
                        onAddTask = { text ->
                            tasks.add(Task(text = text))
                        },
                        schedules = schedules,
                        onAddSchedule = { title, hours, minutes ->
                            schedules.add(Schedule(title = title, hours = hours, minutes = minutes))
                        },
                        showSushi = showSushi,
                        onShowSushiChange = { newValue ->
                            showSushi = newValue
                        },
                        language = language,
                        onLanguageChange = { newLanguage ->
                            language = newLanguage
                        },
                        onBackClick = { currentScreen = Screen.CLOCK }
                    )
                }
            }
        }
    }
}
