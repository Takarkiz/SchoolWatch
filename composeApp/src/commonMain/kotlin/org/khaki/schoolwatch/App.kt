package org.khaki.schoolwatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
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
        var showSushi by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            clockTicker.start()
        }

        when (currentScreen) {
            Screen.CLOCK -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.background
                        )
                        .safeContentPadding(),
                    contentAlignment = Alignment.Center
                ) {
                    if (showSushi) {
                        SushiSecondHandClock()
                    }

                    DigitalClockDisplay(
                        hours = clockTicker.hours.value,
                        minutes = clockTicker.minutes.value,
                        seconds = clockTicker.seconds.value,
                        dateString = clockTicker.currentDate.value,
                        tasks = tasks,
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
            }

            Screen.SETTINGS -> {
                SettingsScreen(
                    tasks = tasks,
                    onAddTask = { text ->
                        tasks.add(Task(text = text))
                    },
                    showSushi = showSushi,
                    onShowSushiChange = { newValue ->
                        showSushi = newValue
                    },
                    onBackClick = { currentScreen = Screen.CLOCK }
                )
            }
        }
    }
}
