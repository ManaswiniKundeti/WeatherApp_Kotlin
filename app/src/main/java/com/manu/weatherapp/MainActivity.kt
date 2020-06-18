package com.manu.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.manu.weatherapp.details.ForecastDetailsFragment
import com.manu.weatherapp.forecast.CurrentForecastFragment
import com.manu.weatherapp.forecast.CurrentForecastFragmentDirections
import com.manu.weatherapp.location.LocationEntryFragment


class MainActivity : AppCompatActivity(),AppNavigator {  // ':' here means EXTENDS

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {    //
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

        val navController = findNavController(R.id.nav_host_fragment_container)
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        //set App toolbar
        //findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setupWithNavController(navController,appBarConfiguration)
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setTitle(R.string.app_name)
        //set bottom navigation view
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)
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
//        val action = LocationEntryFragmentDirections.actionLocationEntryFragmentToCurrentForecastFragment2()
//        //method on an activity to get access to nav controller within the activity
//        findNavController(R.id.nav_host_fragment_container).navigate(action)
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
