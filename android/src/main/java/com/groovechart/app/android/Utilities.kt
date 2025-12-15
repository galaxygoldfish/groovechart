package com.groovechart.app.android

import java.util.Locale

/**
 * Formats large numbers in social media formatting (e.g. 1000000 -> 1M)
 * @param number - The number to format
 * @return - The formatted number as a string
 */
fun formatNumberShort(number: Long): String {
    return when {
        number >= 1_000_000 -> String.format(Locale.US, "%.1fM", number / 1_000_000f)
        number >= 1_000 -> String.format(Locale.US, "%.1fK", number / 1_000f)
        else -> number.toString()
    }
}