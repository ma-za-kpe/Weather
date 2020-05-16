package com.maku.weather.data.repository

import androidx.lifecycle.LiveData
import com.maku.weather.data.db.dao.CountryWeatherDao
import com.maku.weather.data.db.dao.TodayWeatherDao
import com.maku.weather.data.db.dao.WeatherDetailsDao
import com.maku.weather.data.db.entity.Main
import com.maku.weather.data.db.entity.Sys
import com.maku.weather.data.db.entity.Weather
import com.maku.weather.data.network.interfaces.datasource.WeatherNetworkDataSource
import com.maku.weather.data.network.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import timber.log.Timber

class ForecastRepositoryImpl(
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val todayWeatherDao: TodayWeatherDao,
    private val weatherDetailsDao: WeatherDetailsDao,
    private val countryWeatherDao: CountryWeatherDao
) : ForecastRepository {

    init {
        //observe forever because repos do not have a lifecycle
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{newcurrentweather ->
            //persist the weather data in room (CACHING)
            persistFetchedCurrentWeather(newcurrentweather)
        }
    }

    private fun persistFetchedCurrentWeather(newcurrentweather: WeatherResponse) {
        //Global scope doesnt return anything, and lifecycle changes dont affect it
        GlobalScope.launch(Dispatchers.IO) {
            todayWeatherDao.upsert(newcurrentweather.main)
            weatherDetailsDao.upsert(newcurrentweather.weather)
            countryWeatherDao.upsert(newcurrentweather.sys)
        }
    }


    override suspend fun getCurrentWeather(): LiveData<Main> {
        initWeatherData()
        //withcontext returns something
        return withContext(Dispatchers.IO) {
            return@withContext todayWeatherDao.getCurrentWeather()
        }
    }

    override suspend fun getWeatherDetails(): LiveData<Weather> {
        initWeatherData()
        val details  = weatherDetailsDao.getWeatherDetails()
        Timber.d("details ...." + details)
        //withcontext returns something
        return withContext(Dispatchers.IO) {
            return@withContext weatherDetailsDao.getWeatherDets()
        }
    }

    override suspend fun getCountryDetails(): LiveData<Sys> {
        initWeatherData()
        val details  = weatherDetailsDao.getWeatherDetails()
        Timber.d("details ...." + details)
        //withcontext returns something
        return withContext(Dispatchers.IO) {
            return@withContext countryWeatherDao.getCountryWeather()
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
