package com.groovechart.app.android.component

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * List item for URL-image based content (tracks, artists, albums, etc.) containing
 * a title, subtitle, and icon that is fetched from provided URL
 * @param title The title of the content
 * @param subtitle The subtitle of the content (displayed underneath the title)
 * @param iconUrl The URL of the icon to display
 * @param onClick The action to perform when the item is clicked
 * @param modifier The modifier to apply to the item
 * @param iconCircular Whether the icon should be circular or square
 */
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

/**
 * Skeleton to be used when loading the ContentListItem
 * @param modifier The modifier to apply to the skeleton
 * @param circular Whether the skeleton icon should be circular or square
 */
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