package com.maku.weather.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

const val COUNTRY_WEATHER_ID = 0
@Entity(tableName = "country_weather")
data class Sys(
    val country: String
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = COUNTRY_WEATHER_ID
}
