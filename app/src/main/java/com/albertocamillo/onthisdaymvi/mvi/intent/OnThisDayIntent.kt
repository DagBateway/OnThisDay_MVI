package com.albertocamillo.onthisdaymvi.mvi.intent

import com.albertocamillo.onthisdaymvi.data.model.HistoricalEvent
import java.time.LocalDate

// ============================
// OnThisDayIntent.kt
// ============================
/**
 * Describes all the user-driven actions the UI can trigger.
 *
 * This sealed class defines the full set of 'Intents' in the MVI pattern,
 * such as loading today's events, navigating between days, and selecting an event.
 */

sealed class OnThisDayIntent {
    object LoadToday : OnThisDayIntent()
    data class LoadDate(val date: LocalDate) : OnThisDayIntent()
    object NextDate : OnThisDayIntent()
    object PreviousDate : OnThisDayIntent()
    data class SelectEvent(val event: HistoricalEvent) : OnThisDayIntent()
    object ClearSelectedEvent : OnThisDayIntent()
}