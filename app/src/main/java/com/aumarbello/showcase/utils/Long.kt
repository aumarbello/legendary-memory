package com.aumarbello.showcase.utils

import java.text.NumberFormat
import java.util.*

fun Long.formatPrice(): String {
    val format = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 0
    }

    return "₦${format.format(this)}"
}