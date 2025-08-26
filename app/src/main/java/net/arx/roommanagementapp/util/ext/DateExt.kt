package net.arx.roommanagementapp.util.ext

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Calendar.formatDate(): String {
    val formatter = SimpleDateFormat("EEEE d MMMM", Locale.getDefault())
    return formatter.format(this.time) ?: ""
}