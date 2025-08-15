package com.groovechart.app.android.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.groovechart.app.android.R
import com.groovechart.app.android.consts.ButtonSize

/**
 * Custom top app bar that contains a page title and back navigation button
 * @param text The title of the page
 * @param onBackClick The action to perform when the back button is clicked
 */
@Composable
fun LargeHeader(
    text: String,
    onBackClick: () -> Unit
) {
    Column {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 20.dp, start = 7.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_arrow_back),
                contentDescription = stringResource(R.string.cdesc_icon_arrow_back),
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 5.dp, start = 20.dp)
        )
    }
}

/**
 * Section header that has primary and secondary labels as well as an action button at the end
 * @param label The primary label of the section
 * @param secondaryLabel The secondary label of the section (displayed with a lower opacity)
 * @param onButtonClick The action to perform when the action button is clicked
 * @param icon The icon to display on the action button
 * @param contentDescription The content description of the icon for the action button
 * @param modifier The modifier to apply to the section header
 */
@Composable
fun DashedLabelHeader(
    label: String,
    secondaryLabel: String,
    onButtonClick: () -> Unit,
    icon: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = secondaryLabel,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.5F),
            modifier = Modifier.padding(start = 5.dp)
        )
        Canvas(
            modifier = Modifier
                .weight(1f)
                .height(2.dp)
                .padding(horizontal = 20.dp)
        ) {
            drawLine(
                color = Color.LightGray,
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 10f)),
                strokeWidth = size.height
            )
        }
        ActionButton(
            icon = icon,
            contentDescription = contentDescription,
            size = ButtonSize.MEDIUM,
            onClick = onButtonClick
        )
    }
}