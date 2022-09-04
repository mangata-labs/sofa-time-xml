package com.mangata.core_ui.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


fun hideKeyboard(activity: Activity) {
    val inputManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view = activity.currentFocus
    if (view == null) { view = View(activity) }
    inputManager.hideSoftInputFromWindow(view.windowToken, 0)
}