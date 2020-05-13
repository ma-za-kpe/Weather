package com.maku.weather.data.repository

import androidx.lifecycle.LiveData
import com.maku.weather.data.db.dao.TodayWeatherDao
import com.maku.weather.data.db.entity.Main
import com.maku.weather.data.network.interfaces.datasource.WeatherNetworkDataSource
import com.maku.weather.data.network.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val todayWeatherDao: TodayWeatherDao
) : ForecastRepository {

    init {
        //observe forever because repos do not have a lifecycle
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{newcurrentweather ->
            //persist the weather data in room (CACHING)
            persistFetchedCurrentWeather(newcurrentweather)
        }
    }

    private fun persistFetchedCurrentWeather(newcurrentweather: WeatherResponse?) {
        //Global scope doesnt return anything, and lifecycle changes dont affect it
        GlobalScope.launch(Dispatchers.IO) {
            todayWeatherDao.upsert(newcurrentweather!!.main)
        }
    }


    override suspend fun getCurrentWeather(): LiveData<Main> {
        initWeatherData()
        //withcontext returns something
        return withContext(Dispatchers.IO) {
            return@withContext todayWeatherDao.getCurrentWeather()
        }
    }

    // network call which will initiate the first cashing of data inside the database
    private suspend fun initWeatherData() {

        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    //
    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather("arua")
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}
