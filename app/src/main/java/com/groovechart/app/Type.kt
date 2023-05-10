package com.groovechart.app

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalTextApi::class)
val PathwayExtreme = FontFamily(
    Font(
        resId = R.font.pathway_extreme,
        variationSettings = FontVariation.Settings(FontVariation.weight(400)),
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.pathway_extreme,
        variationSettings = FontVariation.Settings(FontVariation.weight(500)),
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.pathway_extreme,
        variationSettings = FontVariation.Settings(FontVariation.weight(700)),
        weight = FontWeight.Bold
    )
)

val Typography = Typography(
    displayLarge = TextStyle(fontFamily = PathwayExtreme),
    displayMedium = TextStyle(fontFamily = PathwayExtreme),
    displaySmall = TextStyle(fontFamily = PathwayExtreme),
    headlineLarge = TextStyle(fontFamily = PathwayExtreme),
    headlineMedium = TextStyle(fontFamily = PathwayExtreme),
    headlineSmall = TextStyle(fontFamily = PathwayExtreme),
    titleLarge = TextStyle(fontFamily = PathwayExtreme),
    titleMedium = TextStyle(fontFamily = PathwayExtreme),
    titleSmall = TextStyle(fontFamily = PathwayExtreme),
    bodyLarge = TextStyle(fontFamily = PathwayExtreme),
    bodyMedium = TextStyle(fontFamily = PathwayExtreme),
    bodySmall = TextStyle(fontFamily = PathwayExtreme),
    labelLarge = TextStyle(fontFamily = PathwayExtreme),
    labelMedium = TextStyle(fontFamily = PathwayExtreme),
    labelSmall = TextStyle(fontFamily = PathwayExtreme)
)