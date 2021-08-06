package com.aumarbello.showcase.utils

import java.lang.Exception
import java.lang.IndexOutOfBoundsException
import java.text.SimpleDateFormat
import java.util.*

fun String.formatDate(): String {
    return try {
        val inFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'",
            Locale.getDefault()
        )
        val date = inFormat.parse(this)
        val outFormat = SimpleDateFormat(
            "dd MMM yyyy", Locale.getDefault()
        )
        date?.let { outFormat.format(it) } ?: ""
    } catch (ex: Exception) {
        ex.printStackTrace()
        ""
    }
}

fun String.toInitialCap(): String {
    val firstChar = get(0)
    return if (firstChar.isLowerCase()) {
        replaceFirst(firstChar, firstChar.titlecaseChar())
    } else
        this
}

fun String.parseBrand(): String {
    return try {
        split(" ")[0]
    } catch (ex: IndexOutOfBoundsException) {
        ex.printStackTrace()
        ""
    }
}

fun String.isVideoUrl(): Boolean {
    return endsWith(".mp4")
}