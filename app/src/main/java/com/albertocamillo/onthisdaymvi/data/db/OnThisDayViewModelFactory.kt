package com.albertocamillo.onthisdaymvi.data.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.albertocamillo.onthisdaymvi.mvi.viewmodel.OnThisDayViewModel

class OnThisDayViewModelFactory(
    private val dao: OnThisDayDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OnThisDayViewModel(dao) as T
    }
}