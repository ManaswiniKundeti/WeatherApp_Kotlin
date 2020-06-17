package com.manu.weatherapp.forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.manu.weatherapp.*

import com.manu.weatherapp.details.ForecastDetailsActivity

class CurrentForecastFragment : Fragment() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    //Used to go back to location entry frag
    private lateinit var appNavigator : AppNavigator
    override fun onAttach(context: Context) {  //fragment lifecycle method
        super.onAttach(context)
        appNavigator = context as AppNavigator  //'as' is used to cast a var to another type
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val zipcode = arguments!!.getString(KEY_ZIPCODE) ?: ""

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        //To go back to location entry frag
        val locationEntryButton = view.findViewById<FloatingActionButton>(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener {
            appNavigator.navigateToLocationEntry()
        }

        //Display Recycler view
        val forecastList: RecyclerView = view.findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(requireContext())
        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){ forecastItem ->
            showForecastDetails(forecastItem)
        }
        forecastList.adapter = dailyForecastAdapter

        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems ->
            //update list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

        //Load forecast based on zipcode
        forecastRepository.loadForecast(zipcode)

        return view
    }

    /**
     * Method used to navigate to weather details. temp & desc details passed used Intent
     */
    private fun showForecastDetails(forecast : DailyForecast){
        val forecastDetailsIntent = Intent(requireContext(), ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp", forecast.temp)
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailsIntent)
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
