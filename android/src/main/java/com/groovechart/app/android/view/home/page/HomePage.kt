package com.groovechart.app.android.view.home.page

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.groovechart.app.android.R
import com.groovechart.app.android.component.ChipButton
import com.groovechart.app.android.component.ContentListItem
import com.groovechart.app.android.component.ContentListItemSkeleton
import com.groovechart.app.android.component.DashedLabelHeader
import com.groovechart.app.android.consts.NavDestinationKey
import com.groovechart.app.android.consts.PreferenceKey
import com.groovechart.app.android.consts.TopItemType
import com.groovechart.app.android.formatNumberShort
import com.groovechart.app.android.viewmodel.HomeViewModel
import com.groovechart.app.networking.consts.TimeRange
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomePage(viewModel: HomeViewModel, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val activityContext = LocalActivity.current
    val pullToRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = !viewModel.loadingDataComplete,
        state = pullToRefreshState,
        onRefresh = {
            coroutineScope.launch {
                activityContext?.let { viewModel.fetch(it) }
            }
        },
        modifier = Modifier.fillMaxSize(),
        indicator = {
            Indicator(
                modifier = Modifier. align(Alignment.TopCenter),
                isRefreshing = !viewModel.loadingDataComplete,
                state = pullToRefreshState ,
                color = MaterialTheme.colorScheme.background,
                containerColor = MaterialTheme.colorScheme.onBackground
            )
        }
    ) {
        AnimatedVisibility(
            visible = !viewModel.loadingDataComplete,
            modifier = Modifier.shimmer(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Column(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .height(30.dp)
                        .fillMaxWidth(0.7F)
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colorScheme.onBackground.copy(0.3F))
                ) {}
                FlowRow(modifier = Modifier.padding(top = 15.dp)) {
                    repeat(times = 5) {
                        Box(
                            modifier = Modifier
                                .padding(end = 7.dp, bottom = 7.dp)
                                .height(35.dp)
                                .fillMaxWidth(0.3F)
                                .clip(MaterialTheme.shapes.large)
                                .background(MaterialTheme.colorScheme.onBackground.copy(0.3F))
                        )
                    }
                }
                repeat(times = 2) {
                    Column(
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 10.dp)
                            .height(40.dp)
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.large)
                            .background(MaterialTheme.colorScheme.onBackground.copy(0.3F))
                    ) {}
                    repeat(times = 4) {
                        ContentListItemSkeleton(Modifier.padding(top = 10.dp))
                    }
                }
            }
        }
        // Actual content
        AnimatedVisibility(
            visible = viewModel.loadingDataComplete
                    && viewModel.topGenreList.isNotEmpty()
                    && viewModel.topSongList.isNotEmpty()
                    && viewModel.topArtistList.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column {
                Spacer(Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    viewModel.arrangementOrder.forEach { currentItem ->
                        when (currentItem.trim()) {
                            stringResource(R.string.settings_homepage_rearrange_artists) -> {
                                DashedLabelHeader(
                                    label = stringResource(R.string.home_page_artist_header),
                                    secondaryLabel = stringResource(
                                        R.string.home_page_past_template,
                                        viewModel.mmkv.decodeString(
                                            PreferenceKey.PREFERENCE_ARTIST_TIME,
                                            "month"
                                        )!!.lowercase()
                                    ),
                                    onButtonClick = {
                                        navController.navigate(
                                            "${NavDestinationKey.TopItemList}/${TopItemType.ARTISTS}"
                                        )
                                    },
                                    icon = painterResource(R.drawable.icon_arrow_forward),
                                    contentDescription = stringResource(R.string.cdesc_icon_arrow_forward),
                                    modifier = Modifier.padding(top = 20.dp)
                                )
                                viewModel.topArtistList.forEach { artist ->
                                    ContentListItem(
                                        title = artist.name,
                                        subtitle = formatNumberShort(artist.followers!!.total.toLong()) + " followers",
                                        iconUrl = artist.images!![0].url,
                                        modifier = Modifier.padding(top = 10.dp),
                                        iconCircular = true,
                                        contentUrl = artist.external_urls.spotify,
                                        context = activityContext!!
                                    )
                                }
                            }
                            stringResource(R.string.settings_homepage_rearrange_tracks) -> {
                                DashedLabelHeader(
                                    label = stringResource(R.string.home_page_track_header),
                                    secondaryLabel = stringResource(
                                        R.string.home_page_past_template,
                                        viewModel.mmkv.decodeString(
                                            PreferenceKey.PREFERENCE_TRACK_TIME,
                                            "month"
                                        )!!.lowercase()
                                    ),
                                    onButtonClick = {
                                        navController.navigate(
                                            "${NavDestinationKey.TopItemList}/${TopItemType.TRACKS}"
                                        )
                                    },
                                    icon = painterResource(R.drawable.icon_arrow_forward),
                                    contentDescription = stringResource(R.string.cdesc_icon_arrow_forward),
                                    modifier = Modifier.padding(top = 20.dp)
                                )
                                viewModel.topSongList.forEach { song ->
                                    ContentListItem(
                                        title = song.name,
                                        subtitle = song.album.artists[0].name,
                                        iconUrl = song.album.images[0].url,
                                        modifier = Modifier.padding(top = 10.dp),
                                        contentUrl = song.external_urls.spotify,
                                        context = activityContext!!
                                    )
                                }
                            }
                            else -> {
                                Text(
                                    text = stringResource(R.string.home_page_genre_header),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(top = 25.dp)
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
                    Spacer(Modifier.height(40.dp))
                }
            }
        }
    }
}