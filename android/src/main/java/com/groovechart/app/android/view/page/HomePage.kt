package com.groovechart.app.android.view.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.groovechart.app.android.R
import com.groovechart.app.android.component.ChipButton
import com.groovechart.app.android.viewmodel.HomeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomePage(viewModel: HomeViewModel) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = stringResource(R.string.home_page_genre_header),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 40.dp)
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