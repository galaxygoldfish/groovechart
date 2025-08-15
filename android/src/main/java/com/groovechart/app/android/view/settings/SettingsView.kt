package com.groovechart.app.android.view.settings

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.groovechart.app.android.GroovechartTheme
import com.groovechart.app.android.R
import com.groovechart.app.android.component.ChipButton
import com.groovechart.app.android.component.LargeButton
import com.groovechart.app.android.component.LargeHeader
import com.groovechart.app.android.consts.AppTheme
import com.groovechart.app.android.consts.ButtonVariant
import com.groovechart.app.android.consts.NavDestinationKey
import com.groovechart.app.android.viewmodel.SettingsViewModel

@Composable
fun SettingsView(navController: NavController) {
    val viewModel: SettingsViewModel = viewModel()
    LaunchedEffect(true) {
        viewModel.fetchThemeSelection()
    }
    GroovechartTheme(
        darkTheme = when (viewModel.currentThemeSelection) {
            AppTheme.THEME_AUTO -> true
            AppTheme.THEME_LIGHT -> false
            else -> isSystemInDarkTheme()
        }
    ) {
        Surface {
            Column(Modifier.statusBarsPadding()) {
                LargeHeader(
                    text = stringResource(R.string.settings_header),
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = stringResource(R.string.settings_theme_header),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    Row(Modifier.padding(top = 15.dp)) {
                        ChipButton(
                            text = stringResource(R.string.settings_theme_auto),
                            selected = viewModel.currentThemeSelection == AppTheme.THEME_AUTO,
                            onClick = {
                                viewModel.apply {
                                    currentThemeSelection = AppTheme.THEME_AUTO
                                    saveThemePreference(AppTheme.THEME_AUTO)
                                }
                            }
                        )
                        ChipButton(
                            text = stringResource(R.string.settings_theme_light),
                            modifier = Modifier.padding(start = 10.dp),
                            selected = viewModel.currentThemeSelection == AppTheme.THEME_LIGHT,
                            onClick = {
                                viewModel.apply {
                                    currentThemeSelection = AppTheme.THEME_LIGHT
                                    saveThemePreference(AppTheme.THEME_LIGHT)
                                }
                            }
                        )
                        ChipButton(
                            text = stringResource(R.string.settings_theme_dark),
                            modifier = Modifier.padding(start = 10.dp),
                            selected = viewModel.currentThemeSelection == AppTheme.THEME_DARK,
                            onClick = {
                                viewModel.apply {
                                    currentThemeSelection = AppTheme.THEME_DARK
                                    saveThemePreference(AppTheme.THEME_DARK)
                                }
                            }
                        )
                    }
                    Text(
                        text = stringResource(R.string.settings_homepage_rearrange_header),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    LargeButton(
                        text = stringResource(R.string.settings_rearrange_button),
                        icon = painterResource(R.drawable.icon_view_headline),
                        contentDescription = stringResource(R.string.cdesc_icon_view_headline),
                        variant = ButtonVariant.OUTLINED,
                        onClick = { navController.navigate(NavDestinationKey.HomepageRearrange) },
                        modifier = Modifier.padding(top = 20.dp)
                    )
                    Text(
                        text = stringResource(R.string.settings_account_header),
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    LargeButton(
                        text = stringResource(R.string.home_dialog_button_logout),
                        icon = painterResource(R.drawable.icon_logout),
                        contentDescription = stringResource(R.string.cdesc_icon_logout),
                        variant = ButtonVariant.FILLED,
                        onClick = { /** LOGOUT */ },
                        modifier = Modifier.padding(top = 20.dp)
                    )
                }
            }
        }
    }
}