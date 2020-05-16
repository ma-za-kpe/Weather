package com.maku.weather.ui.fragment.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maku.weather.R
import com.maku.weather.databinding.FragmentTodayBinding
import com.maku.weather.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import timber.log.Timber

class TodayFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: TodayViewModelFactory by instance<TodayViewModelFactory>()
    private lateinit var homeViewModel: TodayViewModel
    private lateinit var todayBinding: FragmentTodayBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //presavation of viewmodels is a job of the viewmodelprovider
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(TodayViewModel::class.java)

        // Inflate the layout for this fragment
        todayBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, container, false)

//        val service = WeatherService()
//
//        GlobalScope.launch(Dispatchers.Main) {
//            val current = service.getCurrentWeather("arua").await()
//            textView.text = current.toString()
//            Timber.d("respose " + current)
//        }
        bindUI()
        return todayBinding.root
    }

    private fun bindUI()  = launch {
        Timber.d("on bindUI")

        val todayWeather = homeViewModel.weather.await()
        todayWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer //return from observer beace the db could be empty
            Timber.d("weather today %s", it.toString())
            todayBinding.textHome.text = it.toString()
        })

        val weatherDetails = homeViewModel.weatherdetails.await()
        weatherDetails.observe(viewLifecycleOwner, Observer {weather ->
            if (weather == null) return@Observer //return from observer beace the db could be empty
            Timber.d("weather details %s", weather)
            todayBinding.home.text = weather.toString()
        })

    }
}
