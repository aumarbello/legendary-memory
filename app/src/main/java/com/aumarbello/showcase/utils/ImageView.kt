package com.aumarbello.showcase.utils

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load

fun ImageView.load(url: String) {
    val loader = ImageLoader.Builder(context)
        .componentRegistry { add(SvgDecoder(context)) }
        .build()

    load(url, loader) {
        crossfade(true)
    }
}