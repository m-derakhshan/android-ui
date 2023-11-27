package com.theoplayer.android.ui

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * <a href="https://m3.material.io/components/icon-button/overview" class="external" target="_blank">Material Design standard icon button</a>.
 *
 * Equivalent to [androidx.compose.material3.IconButton],
 * but uses [Modifier.defaultMinSize] instead of [Modifier.size] to allow for larger icons.
 *
 * @param onClick called when this button is clicked
 * @param modifier the [Modifier] to be applied to this button
 * @param enabled controls the enabled state of this button. When `false`, this component will not
 * respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param colors [ButtonColors] that will be used to resolve the colors for this button in different
 * states. See [IconButtonDefaults.iconButtonColors].
 * @param contentPadding the spacing values to apply internally between the container and the
 * content
 * @param interactionSource the [MutableInteractionSource] representing the stream of [Interaction]s
 * for this button. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this button in different states.
 */
@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = IconButtonDefaults.iconButtonColors(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    TextButton(
        modifier = modifier
            .defaultMinSize(
                minWidth = IconButtonSize,
                minHeight = IconButtonSize
            ),
        shape = androidx.compose.material3.IconButtonDefaults.filledShape,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        onClick = onClick,
        content = { content() }
    )
}

private const val DisabledIconOpacity = 0.38f
private val IconButtonSize = 40.dp

/**
 * Contains the default values used by icon buttons.
 */
object IconButtonDefaults {
    /**
     * Creates a [ButtonColors] that represents the default colors used in a [IconButton].
     *
     * Equivalent to [androidx.compose.material3.IconButtonDefaults.iconButtonColors],
     * but as [ButtonColors] instead of [androidx.compose.material3.IconButtonColors].
     *
     * @param containerColor the container color of this icon button when enabled.
     * @param contentColor the content color of this icon button when enabled.
     * @param disabledContainerColor the container color of this icon button when not enabled.
     * @param disabledContentColor the content color of this icon button when not enabled.
     */
    @Composable
    fun iconButtonColors(
        containerColor: Color = Color.Transparent,
        contentColor: Color = LocalContentColor.current,
        disabledContainerColor: Color = Color.Transparent,
        disabledContentColor: Color = contentColor.copy(alpha = DisabledIconOpacity)
    ): ButtonColors {
        return ButtonDefaults.textButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        )
    }
}