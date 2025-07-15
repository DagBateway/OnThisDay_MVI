package com.albertocamillo.onthisdaymvi.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "on_this_day")
data class OnThisDayEntity(
    @PrimaryKey val date: String, // format: MM-DD
    val json: String // store full JSON response as string
)