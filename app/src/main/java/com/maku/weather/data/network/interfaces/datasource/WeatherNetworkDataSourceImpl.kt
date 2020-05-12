package com.maku.weather.data.network.interfaces.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maku.weather.data.db.entity.WeatherResponse
import com.maku.weather.data.network.interfaces.service.WeatherService
import com.maku.weather.internal.NoConnectivityException
import timber.log.Timber

class WeatherNetworkDataSourceImpl(
    private val weatherService: WeatherService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<WeatherResponse>() // can be changed
    override val downloadedCurrentWeather: LiveData<WeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchedCurrentWeatherData = weatherService
                .getCurrentWeather("arua")
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeatherData)
            Timber.d("data " + fetchedCurrentWeatherData)
        } catch (e: NoConnectivityException){
            Timber.e( "No internet connectivity. " + e)
        }
    }
}
