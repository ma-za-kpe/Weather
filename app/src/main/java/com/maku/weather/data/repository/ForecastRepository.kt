package com.maku.weather.data.repository

import androidx.lifecycle.LiveData
import com.maku.weather.data.db.entity.Main
import com.maku.weather.data.db.entity.Sys
import com.maku.weather.data.db.entity.Weather

interface ForecastRepository {
    // suspend enables us to call the function from a coroutine
    suspend fun getCurrentWeather(): LiveData<Main>
    suspend fun getWeatherDetails(): LiveData<Weather>
    suspend fun getCountryDetails(): LiveData<Sys>
}
