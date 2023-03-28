package com.theoplayer.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue

@Composable
fun SeekButton(
    modifier: Modifier = Modifier,
    seekOffset: Int = 10,
    iconSize: Dp = 24.dp,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    SeekButton(
        modifier = modifier,
        seekOffset = seekOffset,
        contentPadding = contentPadding
    ) {
        Box {
            Icon(
                Icons.Rounded.Replay,
                modifier = Modifier
                    .size(iconSize)
                    .scale(scaleX = if (seekOffset >= 0) -1f else 1f, scaleY = 1f),
                contentDescription = if (seekOffset >= 0) {
                    "Seek forward by $seekOffset seconds"
                } else {
                    "Seek backward by $seekOffset seconds"
                }
            )
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = iconSize * 0.4f),
                text = "${seekOffset.absoluteValue}",
                fontSize = 6.sp * (iconSize / 24.dp)
            )
        }
    }
}

@Composable
fun SeekButton(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    seekOffset: Int = 10,
    content: @Composable () -> Unit
) {
    val state = LocalTHEOplayer.current
    IconButton(
        modifier = modifier,
        contentPadding = contentPadding,
        onClick = {
            state?.player?.let {
                if (!it.duration.isNaN()) {
                    it.currentTime = (it.currentTime + seekOffset).coerceIn(0.0, it.duration)
                }
            }
        }) {
        content()
    }
}
