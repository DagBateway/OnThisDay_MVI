package com.albertocamillo.onthisdaymvi.data.model

// ============================
// WikimediaResponse.kt
// ============================
/**
 * Kotlin data class matching the structure of the Wikimedia API response.
 *
 * Contains lists of historical [HistoricalEvent]s grouped by type (births, deaths, holidays, events).
 * Used in both network parsing and local cache deserialization.
 */


data class WikimediaResponse(
    val births: List<HistoricalEvent>,
    val deaths: List<HistoricalEvent>,
    val holidays: List<HistoricalEvent>,
    val events: List<HistoricalEvent>
)