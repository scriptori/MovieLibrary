package me.sf.movielibrary.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() = setKeyboardVisible(false)

/**
 * Hide or show the soft keyboard. Should only be called in unusual
 * circumstances where the keyboard is not being hidden automatically.
 */
fun View.setKeyboardVisible(visible: Boolean) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (visible) {
        imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
    } else {
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
