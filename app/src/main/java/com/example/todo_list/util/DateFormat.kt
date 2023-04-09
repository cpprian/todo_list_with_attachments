package com.example.todo_list.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateFormat(): String {
    val date = Date(this)
    val format = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())
    return format.format(date)
}