package com.unlimit.jokes.presentation.jokesscreen.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatTimestamp(timestampMillis: Long): String? {
    val sdf = SimpleDateFormat("dd-MMM-yy h:mm:ss a", Locale.US)
    return sdf.format(Date(timestampMillis))
}