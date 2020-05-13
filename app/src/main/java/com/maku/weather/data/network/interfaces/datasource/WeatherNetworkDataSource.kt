package com.maku.weather.data.network.interfaces.datasource

import androidx.lifecycle.LiveData
import com.maku.weather.data.network.response.WeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<WeatherResponse> // Live data data type, which can be observed for change in the repository class.

    //TODO: find out what suspend does
    suspend fun fetchCurrentWeather( //Asynchronous code ??? Runs separate from the main code.
        location: String
    )
}
