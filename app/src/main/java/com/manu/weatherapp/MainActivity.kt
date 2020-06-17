package com.manu.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.manu.weatherapp.forecast.CurrentForecastFragment
import com.manu.weatherapp.location.LocationEntryFragment


class MainActivity : AppCompatActivity(),AppNavigator {  // ':' here means EXTENDS

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {    //
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

        /** Contains  FRAGMENTS */
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, LocationEntryFragment())
            .commit()
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
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, CurrentForecastFragment.newInstance(zipcode))
            .commit()
    }

    // Navigate back to Location entry
    override fun navigateToLocationEntry() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, LocationEntryFragment())
            .commit()
    }
}
