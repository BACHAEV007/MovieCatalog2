package com.example.kinoteka.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatter {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
    private val serverFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())

    fun formatDate(dateString: String): String {
        return try {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            dateString
        }
    }

    fun parseDateToServerFormat(dateString: String): String {
        return try {
            val date = outputFormat.parse(dateString)
            serverFormat.format(date!!)
        } catch (e: Exception) {
            dateString
        }
    }

    fun formatTime(minutes: Int): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return "${hours} ч ${remainingMinutes} мин"
    }
}