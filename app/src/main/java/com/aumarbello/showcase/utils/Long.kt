package com.aumarbello.showcase.utils

import java.text.NumberFormat
import java.util.*

fun Long.formatPrice(): String {
    val format = NumberFormat.getNumberInstance(Locale("en_NG", "NG")).apply {
        minimumFractionDigits = 2
    }

    return "₦${format.format(this)}"
}