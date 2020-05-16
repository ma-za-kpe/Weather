package com.maku.weather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maku.weather.data.db.entity.COUNTRY_WEATHER_ID
import com.maku.weather.data.db.entity.Sys

@Dao
interface CountryWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(sysEntry: Sys) //update or insert at the same time

    @Query("select * from country_weather where id = $COUNTRY_WEATHER_ID")
    fun getCountryWeather(): LiveData<Sys>
}
