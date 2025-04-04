package com.groovechart.app.android.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.groovechart.app.android.GroovechartTheme
import com.groovechart.app.android.R
import com.groovechart.app.android.component.ButtonVariant
import com.groovechart.app.android.component.LargeButton

@Composable
fun OnboardingView() {
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
                    onClick = {  },
                    modifier = Modifier.padding(bottom = 10.dp, top = 150.dp)
                )
            }
        }
    }
}