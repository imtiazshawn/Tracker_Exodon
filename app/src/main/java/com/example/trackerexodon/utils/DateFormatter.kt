package com.example.trackerexodon.utils

import java.text.SimpleDateFormat
import java.util.Locale
object DateFormatter {
    fun formatDateToHumanReadableForm(dateInMillis: Long): String {
        val dateFormatter = SimpleDateFormat("dd/MM/YYYY", Locale.getDefault())
        return dateFormatter.format(dateInMillis)
    }
}