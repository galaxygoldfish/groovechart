package com.groovechart.app.android.view

import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.DialogHost
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.ImageResult
import com.groovechart.app.android.GroovechartTheme
import com.groovechart.app.android.R
import com.groovechart.app.android.component.ActionButton
import com.groovechart.app.android.component.ButtonSize
import com.groovechart.app.android.component.ButtonVariant
import com.groovechart.app.android.component.LargeButton
import com.groovechart.app.android.consts.NavDestinationKey
import com.groovechart.app.android.consts.PageNavigationKey
import com.groovechart.app.android.view.page.FriendPage
import com.groovechart.app.android.view.page.HomePage
import com.groovechart.app.android.view.page.RecommendationPage
import com.groovechart.app.android.viewmodel.HomeViewModel

@Composable
fun HomeView(navController: NavController) {
    GroovechartTheme {
        val viewModel: HomeViewModel = viewModel()
        val context = LocalActivity.current
        LaunchedEffect(true) {
            context?.let { viewModel.fetch(it) }
        }
        if (viewModel.showAccountDialog) {
            Dialog(
                onDismissRequest = { viewModel.showAccountDialog = false }
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
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
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .clickable(
                                        onClick = {
                                            viewModel.showAccountDialog = true
                                        }
                                    )
                            )
                            Column(modifier = Modifier.padding(start = 20.dp, top = 20.dp)) {
                                Text(
                                    text = viewModel.currentUser!!.display_name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${viewModel.currentUser!!.followers.total} ${
                                        stringResource(
                                            R.string.home_dialog_user_followers
                                        )
                                    }",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(0.7F)
                                )
                            }
                        }
                        ActionButton(
                            icon = painterResource(R.drawable.icon_spotify),
                            contentDescription = stringResource(R.string.cdesc_icon_spotify),
                            size = ButtonSize.MEDIUM,
                            onClick = { /* TODO: Open Spotify */ },
                            modifier = Modifier.padding(top = 20.dp, end = 20.dp)
                        )
                    }
                    Column(modifier = Modifier.padding(20.dp)) {
                        LargeButton(
                            text = stringResource(R.string.home_dialog_button_settings),
                            icon = painterResource(R.drawable.icon_settings),
                            contentDescription = stringResource(R.string.cdesc_icon_settings),
                            variant = ButtonVariant.OUTLINED,
                            onClick = {
                                viewModel.showAccountDialog = false
                                navController.navigate(NavDestinationKey.Settings)
                            }
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
        Box(Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    Row(
                        modifier = Modifier.statusBarsPadding()
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 20.dp, end = 20.dp),
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
                },
                bottomBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .background(MaterialTheme.colorScheme.onBackground.copy(0.1F))
                            .navigationBarsPadding(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val navIconList = listOf(
                            R.drawable.icon_home,
                            R.drawable.icon_people,
                            R.drawable.icon_music_disc
                        )
                        val navLabelList = stringArrayResource(id = R.array.home_nav_labels)
                        navIconList.forEachIndexed { index, icon ->
                            Row(
                                modifier = Modifier
                                    .padding(end = 15.dp, top = 15.dp, bottom = 10.dp)
                                    .padding(start = if (index == 0) 25.dp else 0.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(
                                        if (viewModel.currentPage == index) {
                                            MaterialTheme.colorScheme.onBackground
                                        } else {
                                            Color.Transparent
                                        }
                                    )
                                    .clickable {
                                        viewModel.currentPage = index
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = icon),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(vertical = 7.dp, horizontal = 5.dp)
                                        .size(if (viewModel.currentPage == index) 30.dp else 27.dp),
                                    tint = if (viewModel.currentPage == index) {
                                        MaterialTheme.colorScheme.background
                                    } else {
                                        MaterialTheme.colorScheme.onBackground
                                    }
                                )
                                AnimatedContent(
                                    targetState = viewModel.currentPage == index,
                                    label = "bottom-nav-label",
                                    transitionSpec = {
                                        if (targetState) {
                                            ContentTransform(
                                                targetContentEnter = slideInHorizontally { fullWidth -> fullWidth / 2 } + fadeIn(),
                                                initialContentExit = slideOutHorizontally { fullWidth -> -fullWidth / 2 } + fadeOut()
                                            )
                                        } else {
                                            ContentTransform(
                                                targetContentEnter = slideInHorizontally { fullWidth -> -fullWidth / 2 } + fadeIn(),
                                                initialContentExit = slideOutHorizontally { fullWidth -> fullWidth / 2 } + fadeOut()
                                            )
                                        }
                                    }
                                ) {
                                    if (it) {
                                        Text(
                                            text = navLabelList[index],
                                            style = MaterialTheme.typography.displaySmall,
                                            modifier = Modifier.padding(end = 10.dp),
                                            color = MaterialTheme.colorScheme.background
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                content = { contentPadding ->
                    Column(modifier = Modifier.padding(contentPadding)) {
                        // Transition where it slides left or right in future
                        when (viewModel.currentPage) {
                            PageNavigationKey.Home -> HomePage(viewModel)
                            PageNavigationKey.Friends -> FriendPage(viewModel)
                            PageNavigationKey.Recommendations -> RecommendationPage(viewModel)
                            else -> HomePage(viewModel)
                        }
                    }
                }
            )
        }
    }
}