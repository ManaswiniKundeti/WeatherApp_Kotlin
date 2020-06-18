package com.manu.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.findNavController
import com.manu.weatherapp.details.ForecastDetailsFragment
import com.manu.weatherapp.forecast.CurrentForecastFragment
import com.manu.weatherapp.forecast.CurrentForecastFragmentDirections
import com.manu.weatherapp.location.LocationEntryFragment
import com.manu.weatherapp.location.LocationEntryFragmentDirections


class MainActivity : AppCompatActivity(),AppNavigator {  // ':' here means EXTENDS

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {    //
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

    }

    //create menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator : MenuInflater = menuInflater
        inflator.inflate(R.menu.settings_menu, menu)
        return true
    }

    //handle item selection in menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.tempDisplaySetting -> {
                showTempDisplaySettingDialog(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Navigate to Current forecast along with entered zipcode
    override fun navigateToCurrentForecast(zipcode: String) {
        val action = LocationEntryFragmentDirections.actionLocationEntryFragmentToCurrentForecastFragment2()
        //method on an activity to get access to nav controller within the activity
        findNavController(R.id.nav_host_fragment_container).navigate(action)
    }

    // Navigate back to Location entry
    override fun navigateToLocationEntry() {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment2()
        findNavController(R.id.nav_host_fragment_container).navigate(action)
    }

    override fun naviagteToForecastDetails(forecast: DailyForecast) {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToForecastDetailsFragment(forecast.temp, forecast.description)
        findNavController(R.id.nav_host_fragment_container).navigate(action)
    }


}
