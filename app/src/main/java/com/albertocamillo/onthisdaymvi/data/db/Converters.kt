package com.albertocamillo.onthisdaymvi.data.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {
    @TypeConverter
    fun fromDate(date: LocalDate): String = date.format(DateTimeFormatter.ofPattern("MM-dd"))

    @TypeConverter
    fun toDate(dateString: String): LocalDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MM-dd"))
}