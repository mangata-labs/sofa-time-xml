package com.mangata.core.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date? {
    val pattern = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return pattern.parse(this)
}

fun Date.toYear(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.YEAR)
}