package com.manu.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {

    //private read_only prop : internal to repo
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()

    //create public live data, for activity to actually listen to the update
    val weeklyForecast : LiveData<List<DailyForecast>> = _weeklyForecast

    //Load random temp values and description
    fun loadForecast(zipcode : String){
        val randomValues = List(15){ Random.nextFloat().rem(100) * 100 }
        val forecastItems = randomValues.map {temp ->
            DailyForecast(temp,getTempDescription(temp))
        }
        _weeklyForecast.setValue(forecastItems)
    }

    //customise the description message
    private fun getTempDescription(temp : Float) : String {
        //return if(temp < 75) "It's too cold" else "Weather's great"
        return when(temp){   //when similar to switch
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Colder than I would prefer"
            in 55f.rangeTo(65f) -> "Getting better"
            in 65f.rangeTo(80f) -> "That's the sweet spot"
            in 80f.rangeTo(90f) -> "Getting a little warm"
            in 90f.rangeTo(100f) -> "Where's the A/C"
            in 100f.rangeTo(Float.MAX_VALUE) -> "What is this, Arizona?"
            else -> "Does not compute"
        }
    }

}