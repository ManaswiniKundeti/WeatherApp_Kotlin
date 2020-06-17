package com.manu.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manu.weatherapp.details.ForecastDetailsActivity


class MainActivity : AppCompatActivity() {          // : here means EXTENDS


    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {    //
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tempDisplaySettingManager = TempDisplaySettingManager(this)

        //get reference to the view
        val zipcodeEditText : EditText = findViewById(R.id.zipcodeEditText)
        val enterButton : Button = findViewById(R.id.enterButton)

        enterButton.setOnClickListener {
            val zipcode :String = zipcodeEditText.text.toString()
            if(zipcode.length != 5){
                Toast.makeText(this, R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            }else{
                //Toast.makeText(this, zipcode, Toast.LENGTH_SHORT).show()
                forecastRepository.loadForecast(zipcode)
            }
        }

        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager = LinearLayoutManager(this)

        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){ forecastItem ->
            //week 3
//            val msg = getString(R.string.forecast_clicked_format, forecastItem.temp, forecastItem.description)
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
            showForecastDetails(forecastItem)
        }
        forecastList.adapter = dailyForecastAdapter



        val weeklyForecastObserver = Observer<List<DailyForecast>>{ forecastItems ->
            //update list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }

        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)

    }

    private fun showForecastDetails(forecast : DailyForecast){
        //week 4
        val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp", forecast.temp)
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailsIntent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflator : MenuInflater = menuInflater
        inflator.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //handle item selection
        return when(item.itemId) {
            R.id.tempDisplaySetting -> {
//                Toast.makeText(this, "Clicked menu item", Toast.LENGTH_SHORT).show()
                showTempDisplaySettingDialog(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
