package com.maku.weather.ui.fragment.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.maku.weather.R
import com.maku.weather.data.network.interfaces.service.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class TodayFragment : Fragment() {

    private lateinit var homeViewModel: TodayViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(TodayViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

//        val service = WeatherService()
//
//        GlobalScope.launch(Dispatchers.Main) {
//            val current = service.getCurrentWeather("arua").await()
//            textView.text = current.toString()
//            Timber.d("respose " + current)
//        }

        return root
    }
}
