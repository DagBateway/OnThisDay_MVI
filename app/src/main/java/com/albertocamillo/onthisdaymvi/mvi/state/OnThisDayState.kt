package com.albertocamillo.onthisdaymvi.mvi.state

import com.albertocamillo.onthisdaymvi.data.model.HistoricalEvent
import com.albertocamillo.onthisdaymvi.data.model.WikimediaResponse
import java.time.LocalDate

// ============================
// OnThisDayState.kt
// ============================
/**
 * Describes the current UI state for the "On This Day" screen.
 *
 * Includes the loading state, cached or fetched data, any error messages,
 * the selected date, and optionally the selected event (if viewing details).
 * This state is updated by the ViewModel and observed by the UI.
 */


data class OnThisDayState(
    val isLoading: Boolean = false,
    val data: WikimediaResponse? = null,
    val error: String? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedEvent: HistoricalEvent? = null
)