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
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

@OptIn(ExperimentalTextApi::class)
@Composable
fun GroovechartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // add if (darkTheme) later
    val colors = lightColorScheme(
        surface = Color.White,
        background = Color.White,
        inverseOnSurface = Color.White,
        onSurface = Color.Black,
        surfaceVariant = Color(0XFFF2F2F2)
    )

    val queeringFontFamily = FontFamily(
        Font(resId = R.font.queering, weight = FontWeight.Bold)
    )
    val interFontFamily = FontFamily(
        Font(
            resId = R.font.inter_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(FontWeight.Normal.weight)
            )
        ),
        Font(
            resId = R.font.inter_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(FontWeight.Medium.weight)
            )
        ),
        Font(
            resId = R.font.inter_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(FontWeight.Light.weight)
            )
        )
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
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        ),
        labelMedium = TextStyle(
            fontFamily = interFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 15.sp
        )
    )

    val shapes = Shapes(
        small = RoundedCornerShape(5.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(7.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
