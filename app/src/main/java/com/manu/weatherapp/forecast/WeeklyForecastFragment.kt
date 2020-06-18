package com.manu.weatherapp.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manu.weatherapp.*


class WeeklyForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)

        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""

        //To go back to location entry frag
        val locationEntryButton = view.findViewById<FloatingActionButton>(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        /**
         * DISPLAY & UPDATE RECYCLER VIEW
         */
        val forecastList: RecyclerView = view.findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){ forecastItem ->
            showForecastDetails(forecastItem)
        }
        forecastList.adapter = dailyForecastAdapter

        //Create an observer which updates UI in responce to forecast updates
        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems ->
            //update list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)
        forecastRepository.loadForecast(zipcode)

        return view
    }

    private fun showLocationEntry(){
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun showForecastDetails(forecast : DailyForecast){
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToForecastDetailsFragment(forecast.temp, forecast.description)
        findNavController().navigate(action)
    }

    /**
     * obj scoped to an instance of CurrentForecastFragment
     *  Similar to static method in Java
     *
     *  Creating new instance method is a common pattern for fragments in android
     *  It is like a factory method for frag. It takes in any arg that is needed for the frag to function correctly
     */
    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance (zipcode :String) :WeeklyForecastFragment {

            val fragment = WeeklyForecastFragment()

            //Bundle stores key value pairs. Also used with intents
            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }
    }

}
