package com.albertocamillo.onthisdaymvi.mvi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albertocamillo.onthisdaymvi.data.api.ApiClient
import com.albertocamillo.onthisdaymvi.data.db.OnThisDayDao
import com.albertocamillo.onthisdaymvi.data.db.OnThisDayEntity
import com.albertocamillo.onthisdaymvi.data.model.WikimediaResponse
import com.albertocamillo.onthisdaymvi.mvi.intent.OnThisDayIntent
import com.albertocamillo.onthisdaymvi.mvi.state.OnThisDayState
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// ============================
// OnThisDayViewModel.kt
// ============================
/**
 * Core logic controller for the MVI architecture.
 *
 * It manages state with [StateFlow], handles all [OnThisDayIntent]s,
 * performs API/network operations via [ApiClient], and uses [OnThisDayDao] for local caching.
 *
 * This ViewModel ensures the app works offline by attempting to load from cache first,
 * and updates state accordingly for Compose UI to render.
 */


class OnThisDayViewModel(
    private val dao: OnThisDayDao
) : ViewModel() {
    private val _state = MutableStateFlow(OnThisDayState())
    val state: StateFlow<OnThisDayState> = _state.asStateFlow()

    init {
        handleIntent(OnThisDayIntent.LoadToday)
    }

    fun handleIntent(intent: OnThisDayIntent) {
        when (intent) {
            is OnThisDayIntent.LoadToday -> loadDate(LocalDate.now())
            is OnThisDayIntent.LoadDate -> loadDate(intent.date)
            is OnThisDayIntent.NextDate -> loadDate(_state.value.selectedDate.plusDays(1))
            is OnThisDayIntent.PreviousDate -> loadDate(_state.value.selectedDate.minusDays(1))
            is OnThisDayIntent.SelectEvent -> _state.update {
                it.copy(selectedEvent = intent.event)
            }

            is OnThisDayIntent.ClearSelectedEvent -> _state.update {
                it.copy(selectedEvent = null)
            }
        }
    }

    private fun loadDate(date: LocalDate) {
        viewModelScope.launch {
            val key = date.format(DateTimeFormatter.ofPattern("MM-dd"))
            _state.update { it.copy(isLoading = true, selectedDate = date, error = null) }

            try {
                // Check local DB first
                val cached = dao.getByDate(key)
                if (cached != null) {
                    val localData = Gson().fromJson(cached.json, WikimediaResponse::class.java)
                    _state.update { it.copy(isLoading = false, data = localData) }
                    return@launch
                }

                // If not found locally, fetch from network
                val data = ApiClient.api.getOnThisDay(
                    date.monthValue.toString().padStart(2, '0'),
                    date.dayOfMonth.toString().padStart(2, '0')
                )

                // Save to DB
                dao.insert(OnThisDayEntity(key, Gson().toJson(data)))

                _state.update { it.copy(isLoading = false, data = data) }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "No connection and no cached data available."
                    )
                }
            }
        }
    }
}
