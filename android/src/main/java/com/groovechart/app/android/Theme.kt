package com.groovechart.app.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groovechart.app.android.consts.PreferenceKey
import com.tencent.mmkv.MMKV

@OptIn(ExperimentalTextApi::class)
@Composable
fun GroovechartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    var isDarkTheme = darkTheme
    val mmkv = MMKV.defaultMMKV()
    isDarkTheme = when (mmkv.decodeString(PreferenceKey.PREFERENCE_APP_THEME)) {
        "light" -> false
        "dark" -> true
        else -> isSystemInDarkTheme()
    }
    val colors = if (isDarkTheme) {
        darkColorScheme(
            surface = Color.Black,
            background = Color.Black,
            inverseOnSurface = Color.Black,
            onSurface = Color.White,
            surfaceVariant = Color(0XFFF2F2F2)
        )
    } else {
        lightColorScheme(
            surface = Color.White,
            background = Color.White,
            inverseOnSurface = Color.White,
            onSurface = Color.Black,
            surfaceVariant = Color(0XFFF2F2F2)
        )
    }
    val queeringFontFamily = FontFamily(
        Font(resId = R.font.queering, weight = FontWeight.Bold)
    )
    val interFontFamily = FontFamily(
        Font(resId = R.font.inter_variable)
    )
    val typography = Typography(
        displayLarge = TextStyle(
            fontFamily = queeringFontFamily,
            fontSize = 74.sp
        ),
        displayMedium = TextStyle(
            fontFamily = queeringFontFamily,
            fontSize = 55.sp
        ),
        displaySmall = TextStyle(
            fontFamily = queeringFontFamily,
            fontSize = 32.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        ),
        bodySmall = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        ),
        labelLarge = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.W600,
            fontSize = 17.sp
        ),
        labelMedium = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Thin,
            fontSize = 17.sp
        )
    )

    val shapes = Shapes(
        small = RoundedCornerShape(5.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(5.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
