package com.groovechart.app.android.view.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.groovechart.app.android.R
import com.groovechart.app.android.component.ChipButton
import com.groovechart.app.android.viewmodel.HomeViewModel
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomePage(viewModel: HomeViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Loading skeleton
        AnimatedVisibility(
            visible = !viewModel.loadingDataComplete,
            modifier = Modifier.shimmer(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Column(
                    modifier = Modifier.padding(top = 40.dp)
                        .height(30.dp)
                        .fillMaxWidth(0.7F)
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colorScheme.onBackground.copy(0.3F))
                ) {}
                FlowRow(modifier = Modifier.padding(top = 15.dp)) {
                    repeat(5) {
                        Box(
                            modifier = Modifier.padding(end = 7.dp, bottom = 7.dp)
                                .height(35.dp)
                                .fillMaxWidth(0.3F)
                                .clip(MaterialTheme.shapes.large)
                                .background(MaterialTheme.colorScheme.onBackground.copy(0.3F)))
                    }
                }
            }
        }
        // Actual content
        AnimatedVisibility(
            visible = viewModel.loadingDataComplete,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = stringResource(R.string.home_page_genre_header),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 40.dp)
                )
                FlowRow(modifier = Modifier.padding(top = 15.dp)) {
                    viewModel.topGenreList.forEach { genre ->
                        ChipButton(
                            text = genre.uppercase(),
                            modifier = Modifier.padding(end = 7.dp, bottom = 7.dp)
                        )
                    }
                }
            }
        }
    }
}