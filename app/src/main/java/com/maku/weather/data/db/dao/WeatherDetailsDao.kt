package com.maku.weather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maku.weather.data.db.entity.WEATHER_ID
import com.maku.weather.data.db.entity.Weather

@Dao
interface WeatherDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(detailsEntry: List<Weather>) //update or insert at the same time

    @Query("select * from weather_details where id = $WEATHER_ID")
    fun getWeatherDetails(): LiveData<List<Weather>>

    @Query("select * from weather_details")
    fun getWeatherDets(): LiveData<Weather>
}
