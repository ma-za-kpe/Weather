package com.maku.weather.data.provider

interface LocationProvider {
//    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}
