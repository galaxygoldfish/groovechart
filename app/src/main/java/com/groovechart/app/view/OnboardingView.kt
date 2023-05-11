package com.groovechart.app.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.groovechart.app.R
import com.groovechart.app.theme.GrooveChartTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun OnboardingView(navController: NavController) {
    val pagerState = rememberPagerState()
    GrooveChartTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.onboarding_welcome_header_initial))
                        appendInlineContent(id = "ICON")
                        append(stringResource(id = R.string.app_name))
                        addStyle(
                            SpanStyle(color = MaterialTheme.colorScheme.primary),
                            start = 11,
                            end = 23
                        )
                    },
                    inlineContent = mapOf(
                        "ICON" to InlineTextContent(
                            placeholder = Placeholder(
                                width = 60.sp,
                                height = 60.sp,
                                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                            )
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_groovechart_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(end = 5.dp, top = 5.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    ),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(top = 20.dp, start = 20.dp)
                )
                val primaryGraphicList = listOf(
                    painterResource(id = R.drawable.onboarding_first_primary),
                    painterResource(id = R.drawable.onboarding_second_primary),
                    painterResource(id = R.drawable.onboarding_third_primary)
                )
                val secondaryGraphicList = listOf(
                    painterResource(id = R.drawable.onboarding_first_secondary),
                    painterResource(id = R.drawable.onboarding_second_secondary),
                    painterResource(id = R.drawable.onboarding_third_secondary)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    HorizontalPager(pageCount = 3, state = pagerState) { page ->
                        AnimatedContent(targetState = page, label = "Onboarding pager") {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(0.75F)
                                        .fillMaxHeight(0.35F),
                                    border = BorderStroke(
                                        2.dp,
                                        MaterialTheme.colorScheme.secondary
                                    ),
                                    shape = MaterialTheme.shapes.large,
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                            3.dp
                                        )
                                    )
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize().apply {
                                            if (page != 1) padding(25.dp) else padding(15.dp)
                                        },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = primaryGraphicList[page],
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Icon(
                                            painter = secondaryGraphicList[page],
                                            contentDescription = null,
                                            tint = if (page != 1) {
                                                MaterialTheme.colorScheme.primaryContainer
                                            } else {
                                                MaterialTheme.colorScheme.secondary
                                            },
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                                Text(
                                    text = stringArrayResource(id = R.array.onboarding_captions)[page],
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .fillMaxWidth(0.7F)
                                        .padding(top = 20.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    Row(modifier = Modifier.padding(top = 30.dp)) {
                        repeat(3) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(end = 10.dp)
                                    .clip(CircleShape)
                                    .background(
                                        animateColorAsState(
                                            targetValue =
                                            if (index == pagerState.currentPage) {
                                                MaterialTheme.colorScheme.primary
                                            } else {
                                                MaterialTheme.colorScheme.surfaceVariant
                                            }, label = "Pager dot color"
                                        ).value
                                    )
                                    .size(width = 10.dp, height = 5.dp)
                            ) {}
                        }
                    }
                }
                FilledTonalButton(
                    onClick = { /*TODO*/ },
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.9F)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 15.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 15.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_spotify_logo),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = stringResource(id = R.string.onboarding_login_spotify_button),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 15.dp, bottom = 2.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}