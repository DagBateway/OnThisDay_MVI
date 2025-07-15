package com.albertocamillo.onthisdaymvi.data.db

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// ============================
// Converters.kt
// ============================
/**
 * Contains type converters needed by Room to store complex data types.
 *
 * Since [LocalDate] is not natively supported by SQLite, this converter serialises it
 * as a "MM-dd" formatted string and deserialises it back when loading from the DB.
 */

class Converters {
    @TypeConverter
    fun fromDate(date: LocalDate): String = date.format(DateTimeFormatter.ofPattern("MM-dd"))

    @TypeConverter
    fun toDate(dateString: String): LocalDate =
        LocalDate.parse(dateString, DateTimeFormatter.ofPattern("MM-dd"))
}