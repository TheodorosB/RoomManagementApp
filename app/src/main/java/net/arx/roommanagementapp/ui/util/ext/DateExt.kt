package net.arx.roommanagementapp.ui.util.ext

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Long.formatDate(): String {
    val calendar = Calendar.getInstance().apply { timeInMillis = this@formatDate }
    val formatter = SimpleDateFormat("EEEE d MMMM", Locale.getDefault())
    return formatter.format(calendar.time) ?: ""
}

fun Calendar.dayBounds(): Pair<Long, Long> {
    val start = this.apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis

    val end = this.apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis

    return start to end
}
