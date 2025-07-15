package com.albertocamillo.onthisdaymvi.data.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.albertocamillo.onthisdaymvi.mvi.viewmodel.OnThisDayViewModel

// ============================
// OnThisDayViewModelFactory.kt
// ============================
/**
 * ViewModelProvider.Factory implementation used to construct the [OnThisDayViewModel]
 * with a required [OnThisDayDao] dependency.
 *
 * Needed because ViewModels by default cannot accept custom parameters.
 * This allows us to inject the DAO without using Hilt or other DI tools.
 */


class OnThisDayViewModelFactory(
    private val dao: OnThisDayDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OnThisDayViewModel(dao) as T
    }
}