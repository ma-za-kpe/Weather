package com.maku.weather.ui.fragment.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maku.weather.data.repository.ForecastRepository

//creates new instance of objects.
class TodayViewModelFactory( private val forecastRepository: ForecastRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TodayViewModel(forecastRepository) as T
    }
}
