package com.example.mytraffixaplication.helper

import java.lang.StrictMath.abs
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun extractUsernameFromEmail(email: String?): String {
    val atIndex = email?.indexOf("@")
    if (atIndex != -1) {
        return email?.substring(0, atIndex!!) ?: ""
    }
    return email ?: ""
}

fun calculateElapsedTime(createdDateTime: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentDate = Calendar.getInstance().time
    val createdAtDate = sdf.parse(createdDateTime)

    val diff = abs(currentDate.time - createdAtDate!!.time)
    val diffMinutes = (diff / (60 * 1000)) % 60

    return "$diffMinutes"
}