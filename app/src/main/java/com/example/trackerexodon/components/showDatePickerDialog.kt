package com.example.trackerexodon.components

import android.app.DatePickerDialog
import android.content.Context
import java.util.Calendar

fun showDatePickerDialog(
    context: Context,
    onDateSelected: (dateInMillis: Long) -> Unit
) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}
