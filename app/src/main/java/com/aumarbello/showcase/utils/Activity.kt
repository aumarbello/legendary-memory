package com.aumarbello.showcase.utils

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import com.google.android.material.snackbar.Snackbar

fun Activity.showSnackBar(message: String) {
    currentFocus?.let {
        Snackbar.make(
            it,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }
}

fun Activity.closeKeyboard() {
    val view = this.currentFocus
    view?.let {
        val imm = this.getSystemService<InputMethodManager>()
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}