package com.maku.weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.maku.weather.data.db.dao.TodayWeatherDao
import com.maku.weather.data.db.entity.Main

@Database(
    entities = [Main::class],
    version = 1
)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun todaywetherdao(): TodayWeatherDao

    companion object {
        @Volatile private var instance: WeatherDatabase? = null //all threads will have immediate access to this property - volatile
        private val LOCK = Any() //dummy object to make sure no two threads are doing the same thing

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                WeatherDatabase::class.java, "weather.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
