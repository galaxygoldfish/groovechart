package com.groovechart.app.android.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

object ButtonVariant {
    const val FILLED = 0
    const val OUTLINED = 1
}

object ButtonSize {
    const val SMALL = 0
    const val MEDIUM = 1
    const val LARGE = 2
}

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
            // testing & updating this
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

@Composable
fun ChipButton(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.border(
            1.7.dp,
            MaterialTheme.colorScheme.onSurface,
            MaterialTheme.shapes.large
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(8.dp)
        )
    }
}