package com.dicoding.todoapp.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeConverter {
    fun convertMillisToString(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    fun convertStringToMillis(text: String): Long {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)

        val date = sdf.parse(text)
        date?.let {
            val hour = date.hours
            val minute = date.minutes
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

        }
        return calendar.timeInMillis + (24 * 60 * 60 * 1000)
    }
}