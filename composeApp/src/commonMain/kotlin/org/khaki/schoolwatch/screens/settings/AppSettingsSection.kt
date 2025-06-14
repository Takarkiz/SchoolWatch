package org.khaki.schoolwatch.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.khaki.schoolwatch.localization.Language
import org.khaki.schoolwatch.localization.stringResource
import org.khaki.schoolwatch.theme.DraculaColors

@Composable
fun AppSettingsSection(
    showSushi: Boolean,
    onShowSushiChange: (Boolean) -> Unit,
    language: Language,
    onLanguageChange: (Language) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // App settings section
        Text(
            text = stringResource().appSettings,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // App Settings Section with background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = DraculaColors.Comment.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                // Sushi display toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = stringResource().showSushi,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = showSushi,
                        onCheckedChange = onShowSushiChange
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Language selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = if (language == Language.JAPANESE) "言語" else "Language",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "日本語",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (language == Language.JAPANESE)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Switch(
                            checked = language == Language.ENGLISH,
                            onCheckedChange = { isEnglish ->
                                onLanguageChange(if (isEnglish) Language.ENGLISH else Language.JAPANESE)
                            }
                        )
                        Text(
                            text = "English",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (language == Language.ENGLISH)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}