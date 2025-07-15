package com.albertocamillo.onthisdaymvi.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

// ============================
// OnThisDayEntity.kt
// ============================
/**
 * Room entity representing a cached API response for a specific day.
 *
 * The [date] field (formatted as MM-DD) is the primary key,
 * and the [json] field stores the raw JSON response.
 *
 * This design allows us to cache the full response and deserialize it later
 * using Gson inside the ViewModel.
 */

@Entity(tableName = "on_this_day")
data class OnThisDayEntity(
    @PrimaryKey val date: String, // format: MM-DD
    val json: String // store full JSON response as string
)