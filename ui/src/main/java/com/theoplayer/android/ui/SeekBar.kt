package com.theoplayer.android.ui

import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.theoplayer.android.api.cast.chromecast.PlayerCastState

/**
 * A seek bar showing the current time of the player, and which seeks the player when clicked or dragged.
 *
 * While the user is dragging the seek bar, the player is temporarily paused.
 *
 * @param modifier the [Modifier] to be applied to this seek bar
 * @param colors [SliderColors] that will be used to resolve the colors used for this seek bar in
 * different states. See [SliderDefaults.colors].
 */
@Composable
fun SeekBar(
    modifier: Modifier = Modifier,
    colors: SliderColors = SliderDefaults.colors()
) {
    val player = Player.current
    val currentTime = player?.currentTime?.toFloat() ?: 0.0f
    val seekable = player?.seekable ?: TimeRanges.empty()
    val duration = player?.duration ?: Double.NaN
    val playingAd = player?.playingAd ?: false
    // `player.seekable` is (incorrectly) empty while casting, see #35
    // Temporary fix: always allow seeking while casting.
    val casting = player?.castState == PlayerCastState.CONNECTED
    val enabled = (seekable.isNotEmpty() && !playingAd) || casting

    val valueRange = remember(seekable, duration) {
        seekable.bounds?.let { bounds ->
            bounds.start.toFloat()..bounds.endInclusive.toFloat()
        } ?: run {
            0f..(if (duration.isFinite()) duration.toFloat() else 0f)
        }
    }
    var seekTime by remember { mutableStateOf<Float?>(null) }
    var wasPlayingBeforeSeek by remember { mutableStateOf(false) }

    Slider(
        modifier = modifier.systemGestureExclusion(),
        colors = colors,
        value = seekTime ?: currentTime,
        valueRange = valueRange,
        enabled = enabled,
        onValueChange = remember {
            { time ->
                seekTime = time
                player?.player?.let {
                    if (!it.isPaused) {
                        wasPlayingBeforeSeek = true
                        it.pause()
                    }
                    it.currentTime = time.toDouble()
                }
            }
        },
        // This needs to always be the *same* callback,
        // otherwise Slider will reset its internal SliderState while dragging.
        // https://github.com/androidx/androidx/blob/4d69c45e6361a2e5af77edc9f7f92af3d0db3877/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3/Slider.kt#L270-L282
        onValueChangeFinished = remember {
            {
                seekTime = null
                if (wasPlayingBeforeSeek) {
                    player?.player?.play()
                    wasPlayingBeforeSeek = false
                }
            }
        }
    )
}