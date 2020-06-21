package com.manu.weatherapp.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manu.weatherapp.*
import com.manu.weatherapp.api.CurrentWeather
import com.manu.weatherapp.api.DailyForecast

class CurrentForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()

    private lateinit var locationRepository: LocationRepository

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        val locationName : TextView = view.findViewById(R.id.locationName)
        val tempText : TextView = view.findViewById(R.id.tempText)
        val currentForecastIcon = view.findViewById<ImageView>(R.id.currentForecastIcon)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val zipcode = arguments?.getString(KEY_ZIPCODE) ?: ""

        //Create an observer which updates UI in response to change in weather details
        val currentWeatherObserver = Observer<CurrentWeather> { weather ->
            //load image into the imageview
            val iconId = weather.weather[0].icon
            currentForecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")
            locationName.text = weather.name
            tempText.text =
                weather.forecast.temp.formatTempForDisplay(tempDisplaySettingManager.getTempDisplaySetting())
        }
        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        //Create an observer to listen for change in location, When changes, update UI
        locationRepository = LocationRepository(requireContext())
        //observe changes to the location
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when(savedLocation) {
                is Location.Zipcode -> forecastRepository.loadCurrentForecast(savedLocation.zipcode)
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        //To go back to location entry frag
        val locationEntryButton = view.findViewById<FloatingActionButton>(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        return view
    }

    private fun showLocationEntry(){
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment2()
        findNavController().navigate(action)
    }

    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance (zipcode :String) :CurrentForecastFragment {

            val fragment = CurrentForecastFragment()

            //Bundle stores key value pairs. Also used with intents
            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)
            fragment.arguments = args

            return fragment
        }
    }

}
