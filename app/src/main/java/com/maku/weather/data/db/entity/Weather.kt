package com.maku.weather.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val WEATHER_ID = 0
@Entity(tableName = "weather_details")
data class Weather(
    val description: String,
    val icon: String,
    val main: String
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_ID
}
