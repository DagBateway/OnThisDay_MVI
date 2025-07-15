package com.albertocamillo.onthisdaymvi.mvi.state

import com.albertocamillo.onthisdaymvi.data.model.HistoricalEvent
import com.albertocamillo.onthisdaymvi.data.model.WikimediaResponse
import java.time.LocalDate

data class OnThisDayState(
    val isLoading: Boolean = false,
    val data: WikimediaResponse? = null,
    val error: String? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedEvent: HistoricalEvent? = null
)