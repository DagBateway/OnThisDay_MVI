package com.albertocamillo.onthisdaymvi.data.model

data class WikimediaResponse(
    val births: List<HistoricalEvent>,
    val deaths: List<HistoricalEvent>,
    val holidays: List<HistoricalEvent>,
    val events: List<HistoricalEvent>
)