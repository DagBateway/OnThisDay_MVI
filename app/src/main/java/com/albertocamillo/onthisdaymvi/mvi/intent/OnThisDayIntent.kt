package com.albertocamillo.onthisdaymvi.mvi.intent

import com.albertocamillo.onthisdaymvi.data.model.HistoricalEvent
import java.time.LocalDate

sealed class OnThisDayIntent {
    object LoadToday : OnThisDayIntent()
    data class LoadDate(val date: LocalDate) : OnThisDayIntent()
    object NextDate : OnThisDayIntent()
    object PreviousDate : OnThisDayIntent()
    data class SelectEvent(val event: HistoricalEvent) : OnThisDayIntent()
    object ClearSelectedEvent : OnThisDayIntent()
}