package com.groovechart.app.android.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.groovechart.app.android.GroovechartTheme
import com.groovechart.app.android.R
import com.groovechart.app.android.component.ActionButton
import com.groovechart.app.android.consts.ButtonSize

@Composable
fun AuthenticationFailView() {
    GroovechartTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize()
                    .systemBarsPadding()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                ActionButton(
                    icon = painterResource(R.drawable.icon_close),
                    contentDescription = stringResource(R.string.cdesc_icon_close),
                    onClick = {

                    },
                    size = ButtonSize.SMALL,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}