package com.albertocamillo.onthisdaymvi.data.model

// ============================
// HistoricalEvent.kt
// ============================
/**
 * Represents a single historical event item such as a birth, death, or other notable occurrence.
 * Used inside the [WikimediaResponse] and displayed in the UI.
 */

data class HistoricalEvent(
    val text: String,
    val year: Int
)