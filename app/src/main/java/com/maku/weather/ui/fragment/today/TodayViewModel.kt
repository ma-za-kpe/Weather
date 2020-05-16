package com.maku.weather.ui.fragment.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maku.weather.data.repository.ForecastRepository
import com.maku.weather.internal.lazyDeferred

//
class TodayViewModel(private val foreCastRepository: ForecastRepository) : ViewModel() {

    //called only when needed - lazily
    val weather by lazyDeferred {
        foreCastRepository.getCurrentWeather()
    }

    val weatherdetails by lazyDeferred {
        foreCastRepository.getWeatherDetails()
    }

    val weatherCountry by lazyDeferred {
        foreCastRepository.getCountryDetails()
    }

}
