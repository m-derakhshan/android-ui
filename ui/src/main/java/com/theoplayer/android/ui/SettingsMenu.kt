package com.theoplayer.android.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MenuScope.SettingsMenu() {
    Menu(
        title = { Text(text = "Settings") },
        backIcon = {
            Icon(
                Icons.Rounded.Close,
                tint = Color.White,
                contentDescription = "Close"
            )
        },
    ) {
        val state = LocalTHEOplayer.current
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .alignByBaseline(),
                    text = "Quality"
                )
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .alignByBaseline(),
                    onClick = { openMenu { QualityMenu() } }
                ) {
                    Text(
                        text = state?.activeVideoQuality?.height?.let { "${it}p" } ?: "Automatic",
                        textAlign = TextAlign.Center
                    )
                }
            }
            Row {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .alignByBaseline(),
                    text = "Playback speed"
                )
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .alignByBaseline(),
                    onClick = { openMenu { PlaybackRateMenu() } }
                ) {
                    Text(
                        text = formatPlaybackRate(state?.playbackRate ?: 1.0),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun MenuScope.QualityMenu() {
    Menu(
        title = { Text(text = "Quality") }
    ) {
        QualityList(onClick = { closeCurrentMenu() })
    }
}

@Composable
fun MenuScope.PlaybackRateMenu() {
    Menu(
        title = { Text(text = "Playback speed") }
    ) {
        PlaybackRateList(onClick = { closeCurrentMenu() })
    }
}
