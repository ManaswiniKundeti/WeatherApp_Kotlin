package com.manu.weatherapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

//sealed class hierarchy to represent different loc in app
sealed class Location {
    data class Zipcode(val zipcode : String) : Location()
}

private const val KEY_ZIPCODE = "key_zipcode"

class LocationRepository(context : Context){

    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)


    //expose the saved loc with others using livedata
    private val _savedLocation : MutableLiveData<Location> = MutableLiveData()

    //publicly exposed version of the live data - saveLocation. When _saveLocation changes, User knows as he will observe saveLocation
    val savedLocation : LiveData<Location> = _savedLocation

    //will run when class is created
    init {
        //notified anytime the shared pref change
        preferences.registerOnSharedPreferenceChangeListener{ sharedPreferences, key ->
            if(key != KEY_ZIPCODE) return@registerOnSharedPreferenceChangeListener
            broadcastSavedZipcode()
        }

        //notify observers with the current value
        broadcastSavedZipcode()
    }


    fun saveLocation(location: Location) {
        when(location){
            //any time we call saveLocation, it shrares it with sahred preferences
            is Location.Zipcode ->
                preferences.edit().putString(KEY_ZIPCODE,location.zipcode).apply()
        }
    }

    private fun broadcastSavedZipcode(){
        val zipcode = preferences.getString(KEY_ZIPCODE, "")
        if(zipcode != null && zipcode.isNotBlank()){
            //fwd data to _savedLocation observers
            _savedLocation.value = Location.Zipcode(zipcode)
        }
    }


}
