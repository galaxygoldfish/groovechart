package com.groovechart.app.android.view

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.groovechart.app.android.GroovechartTheme
import com.groovechart.app.android.R
import com.groovechart.app.android.component.LargeButton
import com.groovechart.app.android.consts.ButtonVariant
import com.groovechart.app.android.viewmodel.OnboardingViewModel

@Composable
fun OnboardingView() {
    val viewModel: OnboardingViewModel = viewModel()
    val parentActivityContext = LocalActivity.current
    GroovechartTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.onboarding_background),
                contentDescription = stringResource(R.string.cdesc_onboarding_background),
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(
                modifier = Modifier.fillMaxSize()
                    .systemBarsPadding()
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
                LargeButton(
                    text = stringResource(R.string.welcome_proceed_button_label),
                    icon = painterResource(R.drawable.icon_spotify),
                    contentDescription = stringResource(R.string.cdesc_icon_spotify),
                    variant = ButtonVariant.FILLED,
                    onClick = {
                        parentActivityContext?.let {
                            viewModel.launchUserAuthFlow(it)
                        }
                    },
                    modifier = Modifier.padding(bottom = 20.dp, top = 150.dp)
                )
            }
        }
    }
}