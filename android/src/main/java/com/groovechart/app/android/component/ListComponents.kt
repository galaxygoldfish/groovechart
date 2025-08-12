package com.groovechart.app.android.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

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

@Composable
fun ContentListItemSkeleton(
    modifier: Modifier = Modifier,
    circular: Boolean = false
) {
    Row(modifier = modifier) {
        Spacer(
            modifier = Modifier.size(50.dp)
                .clip(if (circular) CircleShape else MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.onBackground.copy(0.3F))
        )
        Column(modifier = Modifier.padding(start = 15.dp)) {
            Spacer(
                modifier = Modifier.height(20.dp)
                    .fillMaxWidth(0.7F)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.onBackground.copy(0.3F))
            )
            Spacer(
                modifier = Modifier.padding(top = 10.dp)
                    .height(20.dp)
                    .fillMaxWidth(0.5F)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.onBackground.copy(0.3F))
            )
        }
    }
}

@Composable
fun ContentListItem(
    title: String,
    subtitle: String,
    iconUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconCircular: Boolean = false
) {
    Row(
        modifier = modifier.fillMaxWidth().clickable{ onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(iconUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(50.dp)
                .clip(if (iconCircular) CircleShape else MaterialTheme.shapes.medium)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = if (iconCircular) CircleShape else MaterialTheme.shapes.medium
                ),
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier.padding(start = 15.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
