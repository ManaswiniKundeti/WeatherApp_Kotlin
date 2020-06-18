package com.manu.weatherapp

interface AppNavigator {

    fun navigateToCurrentForecast(zipcode : String)

    fun navigateToLocationEntry()

    fun naviagteToForecastDetails(forecast: DailyForecast)
}