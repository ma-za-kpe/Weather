package com.maku.weather.data.repository

import androidx.lifecycle.LiveData
import com.maku.weather.data.db.entity.Main

interface ForecastRepository {
    // suspend enables us to call the function from a coroutine
    suspend fun getCurrentWeather(): LiveData<Main>
}
