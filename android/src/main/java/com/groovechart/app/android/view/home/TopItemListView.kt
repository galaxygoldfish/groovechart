package com.groovechart.app.android.view.home

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.groovechart.app.android.GroovechartTheme
import com.groovechart.app.android.R
import com.groovechart.app.android.component.ContentListItem
import com.groovechart.app.android.component.ContentListItemSkeleton
import com.groovechart.app.android.component.LargeHeader
import com.groovechart.app.android.component.TimeRangeSegmentedControl
import com.groovechart.app.android.consts.TopItemType
import com.groovechart.app.android.formatNumberShort
import com.groovechart.app.android.viewmodel.TopItemListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopItemListView(navController: NavController, type: String) {
    val coroutineScope = rememberCoroutineScope()
    val activityContext = LocalActivity.current
    val pullToRefreshState = rememberPullToRefreshState()
    val viewModel: TopItemListViewModel = viewModel()

    LaunchedEffect(viewModel.selectedTimeRange) {
        when (type) {
            TopItemType.ARTISTS -> {
                viewModel.fetchArtists(viewModel.selectedTimeRange, activityContext!!)
            }
            TopItemType.TRACKS -> {
                viewModel.fetchTracks(viewModel.selectedTimeRange, activityContext!!)
            }
        }
    }

    GroovechartTheme {
        Surface {
            Column(Modifier.statusBarsPadding()) {
                LargeHeader(
                    text = when (type) {
                        TopItemType.ARTISTS -> stringResource(R.string.top_item_title_artists)
                        else -> stringResource(R.string.top_item_title_tracks)
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    twoLine = false
                )
                TimeRangeSegmentedControl(
                    onSelectionChange = { selectedTime ->
                        viewModel.selectedTimeRange = selectedTime
                    },
                    modifier = Modifier.padding(top = 20.dp, end = 20.dp, start = 20.dp)
                )
                PullToRefreshBox(
                    isRefreshing = viewModel.loadingItems,
                    state = pullToRefreshState,
                    onRefresh = {
                        coroutineScope.launch {
                            activityContext?.let {
                                when (type) {
                                    TopItemType.TRACKS -> {
                                        viewModel.fetchTracks(viewModel.selectedTimeRange, it)
                                    }

                                    TopItemType.ARTISTS -> {
                                        viewModel.fetchArtists(viewModel.selectedTimeRange, it)
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    indicator = {
                        Indicator(
                            modifier = Modifier.align(Alignment.TopCenter),
                            isRefreshing = viewModel.loadingItems,
                            state = pullToRefreshState,
                            color = MaterialTheme.colorScheme.background,
                            containerColor = MaterialTheme.colorScheme.onBackground
                        )
                    }
                ) {
                    AnimatedContent(targetState = viewModel.loadingItems) { loading ->
                        Box(Modifier.fillMaxSize()) {
                            LazyColumn {
                                item {
                                    Spacer(Modifier.height(15.dp))
                                }
                                if (loading) {
                                    items(15) {
                                        ContentListItemSkeleton(
                                            circular = type == TopItemType.ARTISTS,
                                            modifier = Modifier.padding(
                                                horizontal = 20.dp,
                                                vertical = 5.dp
                                            )
                                        )
                                    }
                                } else {
                                    when (type) {
                                        TopItemType.ARTISTS -> {
                                            itemsIndexed(viewModel.topArtistList) { index, item ->
                                                ContentListItem(
                                                    title = item.name,
                                                    subtitle = formatNumberShort(item.followers!!.total.toLong()) + " followers",
                                                    iconUrl = item.images!![0].url,
                                                    onClick = { },
                                                    modifier = Modifier.padding(
                                                        horizontal = 20.dp,
                                                        vertical = 5.dp
                                                    ),
                                                    iconCircular = true
                                                )
                                            }
                                        }

                                        TopItemType.TRACKS -> {
                                            itemsIndexed(viewModel.topTrackList) { index, item ->
                                                ContentListItem(
                                                    title = item.name,
                                                    subtitle = item.album.artists[0].name,
                                                    iconUrl = item.album.images[0].url,
                                                    onClick = { },
                                                    modifier = Modifier.padding(
                                                        horizontal = 20.dp,
                                                        vertical = 5.dp
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(
                                modifier = Modifier.height(30.dp)
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                MaterialTheme.colorScheme.background,
                                                Color.Transparent
                                            )
                                        )
                                    )
                                    .align(Alignment.TopCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}