package com.groovechart.app.android.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.ImageResult
import com.groovechart.app.android.GroovechartTheme
import com.groovechart.app.android.R
import com.groovechart.app.android.component.ButtonVariant
import com.groovechart.app.android.component.LargeButton
import com.groovechart.app.android.view.page.HomePage
import com.groovechart.app.android.viewmodel.HomeViewModel

@Composable
fun HomeView() {
    GroovechartTheme {
        val viewModel: HomeViewModel = viewModel()
        LaunchedEffect(true) {
            viewModel.fetch()
        }
        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            AnimatedVisibility(
                visible = viewModel.showAccountDialog,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Black.copy(0.6F))
                ) {
                    Box(modifier = Modifier.fillMaxSize()
                        .clickable(onClick = { viewModel.showAccountDialog = false })
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(0.8F)
                            .clip(MaterialTheme.shapes.large)
                            .align(Alignment.Center)
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(viewModel.currentUser!!.images[0].url)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Profile picture",
                                modifier = Modifier
                                    .padding(top = 20.dp, start = 20.dp)
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .clickable(
                                        onClick = {
                                            viewModel.showAccountDialog = true
                                        }
                                    )
                            )
                            Column(modifier = Modifier.padding(start = 20.dp)) {
                                Text(
                                    text = viewModel.currentUser!!.display_name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "${viewModel.currentUser!!.followers.total} ${stringResource(R.string.home_dialog_user_followers)}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.7F)
                                )
                            }
                        }
                        Column(modifier = Modifier.padding(20.dp)) {
                            LargeButton(
                                text = stringResource(R.string.home_dialog_button_settings),
                                icon = painterResource(R.drawable.icon_settings),
                                contentDescription = stringResource(R.string.cdesc_icon_settings),
                                variant = ButtonVariant.OUTLINED,
                                onClick = { /*TODO*/ }
                            )
                            LargeButton(
                                text = stringResource(R.string.home_dialog_button_app_updates),
                                icon = painterResource(R.drawable.icon_storefront),
                                contentDescription = stringResource(R.string.cdesc_icon_storefront),
                                variant = ButtonVariant.OUTLINED,
                                onClick = { /*TODO*/ },
                                modifier = Modifier.padding(top = 10.dp)
                            )
                            LargeButton(
                                text = stringResource(R.string.home_dialog_button_logout),
                                icon = painterResource(R.drawable.icon_logout),
                                contentDescription = stringResource(R.string.cdesc_icon_logout),
                                variant = ButtonVariant.FILLED,
                                onClick = { /*TODO*/ },
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier.statusBarsPadding()
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayMedium
                )
                AnimatedVisibility(viewModel.currentUser !== null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(viewModel.currentUser!!.images[0].url)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable(
                                onClick = {
                                    viewModel.showAccountDialog = true
                                }
                            )
                    )
                }
            }
            Row {

            }
        }
        HomePage(viewModel)
    }
}