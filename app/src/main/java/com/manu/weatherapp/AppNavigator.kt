package com.manu.weatherapp

interface AppNavigator {

    fun navigateToCurrentForecast(zipcode : String)

    fun navigateToLocationEntry()
}