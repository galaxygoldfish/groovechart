package com.groovechart.app.android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.groovechart.app.android.consts.ButtonSize
import com.groovechart.app.android.consts.ButtonVariant

/**
 * Square, filled icon button with filled container, permitting multiple sizes
 * @param icon The icon resource to be displayed
 * @param contentDescription The content description of the icon
 * @param size The size of the button - use ButtonSize constants
 * @param onClick The callback to be performed when the button is clicked
 * @param modifier The modifier to be applied to the button
 */
@Composable
fun ActionButton(
    icon: Painter,
    contentDescription: String,
    size: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.onSurface)
            .clickable { onClick.invoke() }
            .size(when(size) {
                ButtonSize.SMALL -> 30.dp
                ButtonSize.MEDIUM -> 40.dp
                else -> 50.dp
            })
    ) {
        Icon(
            painter = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.inverseOnSurface,
            modifier = Modifier.padding(10.dp)
                .size(when(size) {
                    ButtonSize.SMALL -> 20.dp
                    ButtonSize.MEDIUM -> 25.dp
                    else -> 30.dp
            })
        )
    }
}

/**
 * Large rectangular button that fills the width of its parent container with text, icon
 * and outline/filled variants available
 * @param text The text to be displayed
 * @param icon The icon resource to be displayed
 * @param contentDescription The content description of the icon
 * @param variant The variant of the button - use ButtonVariant constants
 * @param onClick The callback to be performed when the button is clicked
 * @param modifier The modifier to be applied to the button
 */
@Composable
fun LargeButton(
    text: String,
    icon: Painter,
    contentDescription: String,
    variant: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = when (variant) {
                ButtonVariant.FILLED -> MaterialTheme.colorScheme.onSurface
                else -> MaterialTheme.colorScheme.surface
            },
            contentColor = when (variant) {
                ButtonVariant.FILLED -> MaterialTheme.colorScheme.inverseOnSurface
                else -> MaterialTheme.colorScheme.onSurface
            }
        ),
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth()
            .then(modifier)
            .border(
                width = when (variant) {
                    ButtonVariant.OUTLINED -> 2.dp
                    else -> 0.dp
                },
                color = MaterialTheme.colorScheme.onSurface,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

/**
 * Small, text-based button containing only a label and having selected/unselected states.
 * Displays as outlined when not selected and filled when selected
 * @param text The text to be displayed
 * @param selected Whether the chip is currently selected
 * @param onClick The callback to be performed when the chip is clicked
 * @param modifier The modifier to be applied to the chip
 */
@Composable
fun ChipButton(
    text: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.surface)
            .border(
            1.7.dp,
            MaterialTheme.colorScheme.onSurface,
            MaterialTheme.shapes.large
        ).clickable { onClick.invoke() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(8.dp),
            color = if (selected) MaterialTheme.colorScheme.inverseOnSurface else MaterialTheme.colorScheme.onSurface
        )
    }
}