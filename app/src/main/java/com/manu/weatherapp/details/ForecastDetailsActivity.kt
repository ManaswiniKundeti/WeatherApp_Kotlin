package com.manu.weatherapp.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import com.manu.weatherapp.R
import com.manu.weatherapp.TempDisplaySettingManager
import com.manu.weatherapp.formatTempForDisplay
import com.manu.weatherapp.showTempDisplaySettingDialog

class ForecastDetailsActivity : AppCompatActivity() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast_details)

        tempDisplaySettingManager = TempDisplaySettingManager(this)
        setTitle(R.string.forecast_details)

        //view references
        val tempText = findViewById<TextView>(R.id.tempText)
        val descriptionText = findViewById<TextView>(R.id.descriptionText)

        //Get details from Intent
        val temp = intent.getFloatExtra("key_temp", 0f)
        tempText.text = "${temp.formatTempForDisplay(tempDisplaySettingManager.getTempDisplaySetting())}"
        descriptionText.text = intent.getStringExtra("key_description")

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

}
