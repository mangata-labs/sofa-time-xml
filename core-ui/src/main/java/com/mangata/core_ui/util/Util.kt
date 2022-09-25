package com.mangata.core_ui.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.RecyclerView


fun hideKeyboard(activity: Activity) {
    val inputManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) {
        view = View(activity)
    }
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun dpToPx(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}