package com.groovechart.app.android.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.groovechart.app.android.R

@Composable
fun LargeHeader(
    text: String,
    onBackClick: () -> Unit
) {
    Column {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.padding(top = 20.dp, start = 7.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_arrow_back),
                contentDescription = stringResource(R.string.cdesc_icon_arrow_back),
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = 5.dp, start = 20.dp)
        )
    }
}