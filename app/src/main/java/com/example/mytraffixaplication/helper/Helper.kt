package com.example.mytraffixaplication.helper

fun extractUsernameFromEmail(email: String?): String {
    val atIndex = email?.indexOf("@")
    if (atIndex != -1) {
        return email?.substring(0, atIndex!!) ?: ""
    }
    return email ?: ""
}