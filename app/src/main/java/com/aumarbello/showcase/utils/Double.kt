package com.aumarbello.showcase.utils

import java.text.NumberFormat
import java.util.*

fun Double?.formatRating(): String {
    val format = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 2
    }

    return if (this != null)
        format.format(this)
    else
        ""
}