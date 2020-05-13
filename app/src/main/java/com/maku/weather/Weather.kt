package com.maku.weather

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import com.maku.weather.data.db.WeatherDatabase
import com.maku.weather.data.network.interfaces.connection.ConnectivityInterceptor
import com.maku.weather.data.network.interfaces.connection.ConnectivityInterceptorImpl
import com.maku.weather.data.network.interfaces.datasource.WeatherNetworkDataSource
import com.maku.weather.data.network.interfaces.datasource.WeatherNetworkDataSourceImpl
import com.maku.weather.data.network.interfaces.service.WeatherService
import com.maku.weather.data.repository.ForecastRepository
import com.maku.weather.data.repository.ForecastRepositoryImpl
import com.maku.weather.ui.fragment.today.TodayViewModelFactory
import com.maku.weather.utils.isNight
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import timber.log.Timber

class Weather : Application(), KodeinAware {
    override fun onCreate() {
        super.onCreate()
        //timber
        Timber.plant(Timber.DebugTree())

        //Threeten
        AndroidThreeTen.init(this)

        // Get UI mode and set
        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    //Using Kodein.lazy allows you to access the Context at binding time.
    override val kodein = Kodein.lazy {
        import(androidXModule(this@Weather))

        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().todaywetherdao()}
        bind() from singleton { instance<WeatherDatabase>().wetherdetailsdao()}
//        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
//        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
//        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
//        bind<LocationProvoder>() with singleton { LocationProvoderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance()) }
        bind() from provider { TodayViewModelFactory(instance()) }
//        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
//        bind() from factory { detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate, instance(), instance()) }

    }
}
