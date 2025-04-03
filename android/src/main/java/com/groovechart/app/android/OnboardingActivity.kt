package com.groovechart.app.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.WindowInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.groovechart.app.Greeting

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroovechartTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(R.drawable.onboarding_background),
                        contentDescription = stringResource(R.string.cdesc_onboarding_background),
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.FillBounds
                    )
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .navigationBarsPadding()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Image(
                            painter = painterResource(R.drawable.groovechart_mark),
                            contentDescription = stringResource(R.string.cdesc_groovechart_mark),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.displayLarge,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                        Text(
                            text = stringResource(R.string.welcome_subtitle),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 15.dp)
                        )
                        Button(
                            onClick = {  },
                            modifier = Modifier.fillMaxWidth()
                                .padding(bottom = 10.dp, top = 150.dp),
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.onSurface,
                                contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                                disabledContainerColor = MaterialTheme.colorScheme.scrim,
                                disabledContentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 10.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_spotify),
                                    contentDescription = stringResource(R.string.cdesc_icon_spotify),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = stringResource(R.string.welcome_proceed_button_label),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}