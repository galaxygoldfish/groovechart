package com.groovechart.app.android.view.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.groovechart.app.android.GroovechartTheme
import com.groovechart.app.android.component.LargeHeader
import com.groovechart.app.android.R
import com.groovechart.app.android.component.ActionButton
import com.groovechart.app.android.component.ChipButton
import com.groovechart.app.android.component.LargeButton
import com.groovechart.app.android.consts.ButtonSize
import com.groovechart.app.android.consts.ButtonVariant
import com.groovechart.app.android.consts.NavDestinationKey
import com.groovechart.app.android.consts.PreferenceKey
import com.groovechart.app.android.consts.RearrangeHomeSection
import com.groovechart.app.android.viewmodel.SettingsViewModel
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import kotlin.math.roundToInt

@Composable
fun HomepageRearrangeView(navController: NavController) {
    val viewModel: SettingsViewModel = viewModel()
    val context = LocalContext.current
    val haptics = LocalHapticFeedback.current
    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        haptics.performHapticFeedback(HapticFeedbackType.GestureEnd)
        viewModel.arrangementOrder = viewModel.arrangementOrder.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }
    LaunchedEffect(true) {
        viewModel.fetchArrangementOrder(context)
    }
    GroovechartTheme {
        AnimatedVisibility(viewModel.showEditRearrangeSectionDialog) {
            Dialog(
                onDismissRequest = {
                    viewModel.showEditRearrangeSectionDialog = false
                }
            ) {
                val currentEditCategory = when (viewModel.currentRearrangeSectionEditing) {
                    RearrangeHomeSection.TOP_TRACKS -> stringResource(R.string.settings_homepage_rearrange_tracks)
                    RearrangeHomeSection.TOP_ARTISTS -> stringResource(R.string.settings_homepage_rearrange_artists)
                    else -> stringResource(R.string.settings_homepage_rearrange_genres)
                }
                val currentEditCategoryTimeKey = when (viewModel.currentRearrangeSectionEditing) {
                    RearrangeHomeSection.TOP_TRACKS -> PreferenceKey.PREFERENCE_TRACK_TIME
                    RearrangeHomeSection.TOP_ARTISTS -> PreferenceKey.PREFERENCE_ARTIST_TIME
                    else -> PreferenceKey.PREFERENCE_GENRE_TIME
                }
                val timescaleMedium = stringResource(R.string.settings_timescale_medium)
                var currentEditCategoryTime by remember {
                    mutableStateOf(
                        viewModel.mmkv.decodeString(currentEditCategoryTimeKey, timescaleMedium)
                    )
                }
                val currentEditCategoryAmountKey = when (viewModel.currentRearrangeSectionEditing) {
                    RearrangeHomeSection.TOP_TRACKS -> PreferenceKey.PREFERENCE_TRACK_AMOUNT
                    RearrangeHomeSection.TOP_ARTISTS -> PreferenceKey.PREFERENCE_ARTIST_AMOUNT
                    else -> PreferenceKey.PREFERENCE_GENRE_AMOUNT
                }
                var currentEditCategoryAmount by remember {
                    mutableIntStateOf(viewModel.mmkv.decodeInt(currentEditCategoryAmountKey, 4))
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Text(
                        text = currentEditCategory,
                        style = MaterialTheme.typography.displayMedium,
                        modifier = Modifier.padding(20.dp)
                    )
                    Text(
                        text = stringResource(
                            R.string.settings_homepage_rearrange_time_edit,
                            currentEditCategory.lowercase()
                        ),
                        modifier = Modifier.padding(start = 20.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                    Row(Modifier.padding(top = 15.dp, start = 20.dp, bottom = 15.dp)) {
                        val shortTime = stringResource(R.string.settings_timescale_short)
                        ChipButton(
                            text = shortTime.uppercase(),
                            selected = currentEditCategoryTime == shortTime,
                            onClick = {
                                currentEditCategoryTime = shortTime
                            }
                        )
                        val mediumTime = stringResource(R.string.settings_timescale_medium)
                        ChipButton(
                            text = mediumTime.uppercase(),
                            modifier = Modifier.padding(start = 10.dp),
                            selected = currentEditCategoryTime == mediumTime,
                            onClick = {
                                currentEditCategoryTime = mediumTime
                            }
                        )
                        val longTime = stringResource(R.string.settings_timescale_long)
                        ChipButton(
                            text = longTime.uppercase(),
                            modifier = Modifier.padding(start = 10.dp),
                            selected = currentEditCategoryTime == longTime,
                            onClick = {
                                currentEditCategoryTime = longTime
                            }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(
                                R.string.settings_homepage_rearrange_amount_edit,
                                currentEditCategory.lowercase()
                            ),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = currentEditCategoryAmount.toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Slider(
                        value = currentEditCategoryAmount.toFloat(),
                        onValueChange = { newValue ->
                            currentEditCategoryAmount = newValue.roundToInt()
                            haptics.performHapticFeedback(HapticFeedbackType.SegmentTick)
                        },
                        valueRange = 1f..10f,
                        steps = 8,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.onBackground,
                            activeTrackColor = MaterialTheme.colorScheme.onBackground,
                            inactiveTrackColor = Color.LightGray,
                            activeTickColor = MaterialTheme.colorScheme.onBackground,
                            inactiveTickColor = MaterialTheme.colorScheme.onBackground
                        ),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    LargeButton(
                        text = stringResource(R.string.settings_homepage_rearrange_done),
                        icon = painterResource(R.drawable.icon_check),
                        contentDescription = stringResource(R.string.cdesc_icon_check),
                        variant = ButtonVariant.FILLED,
                        onClick = {
                            viewModel.apply {
                                mmkv.apply {
                                    encode(currentEditCategoryTimeKey, currentEditCategoryTime)
                                    encode(currentEditCategoryAmountKey, currentEditCategoryAmount)
                                }
                                showEditRearrangeSectionDialog = false
                            }
                        },
                        modifier = Modifier.padding(20.dp)
                    )
                }
            }
        }
        Surface {
            Column(
                modifier = Modifier.fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(bottom = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    LargeHeader(
                        text = stringResource(R.string.settings_homepage_rearrange_header),
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                    Text(
                        text = stringResource(R.string.settings_homepage_rearrange_subtitle),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(20.dp)
                    )
                    LazyColumn(state = lazyListState) {
                        items(viewModel.arrangementOrder.size, key = { viewModel.arrangementOrder[it] }) {
                            ReorderableItem(reorderableLazyListState, key = viewModel.arrangementOrder[it]) { isDragging ->
                                val topGenres = stringResource(R.string.settings_homepage_rearrange_genres)
                                val topArtists = stringResource(R.string.settings_homepage_rearrange_artists)
                                HomepageItemCard(
                                    label = viewModel.arrangementOrder[it].trim(),
                                    dragModifier = Modifier.draggableHandle(),
                                    onClick = {
                                        viewModel.currentRearrangeSectionEditing = when (viewModel.arrangementOrder[it].trim()) {
                                            topGenres -> RearrangeHomeSection.TOP_GENRES
                                            topArtists -> RearrangeHomeSection.TOP_ARTISTS
                                            else -> RearrangeHomeSection.TOP_TRACKS
                                        }
                                        viewModel.showEditRearrangeSectionDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
                LargeButton(
                    text = stringResource(R.string.settings_homepage_rearrange_save),
                    icon = painterResource(R.drawable.icon_save),
                    contentDescription = stringResource(R.string.cdesc_icon_save),
                    variant = 0,
                    onClick = {
                        viewModel.saveArrangementPreferences()
                        navController.popBackStack()
                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        }
    }
}

@Composable
fun HomepageItemCard(
    label: String,
    modifier: Modifier = Modifier,
    dragModifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .drawBehind {
                val stroke = Stroke(
                    width = 2.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(20F, 15F))
                )
                drawRoundRect(
                    color = Color.Black,
                    style = stroke,
                    cornerRadius = CornerRadius(5.dp.toPx())
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.icon_view_headline),
                contentDescription = stringResource(R.string.cdesc_icon_view_headline),
                modifier = dragModifier.padding(20.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        ActionButton(
            icon = painterResource(R.drawable.icon_edit_pen),
            contentDescription = stringResource(R.string.cdesc_icon_edit_pen),
            onClick = onClick,
            size = ButtonSize.MEDIUM,
            modifier = Modifier.padding(20.dp)
        )
    }
}