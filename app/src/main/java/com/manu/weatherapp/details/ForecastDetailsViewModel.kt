package com.manu.weatherapp.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")

class ForecastDetailsViewModelFactory(private val args : ForecastDetailsFragmentArgs) : ViewModelProvider.Factory{
    /**
     * Creates a new instance of the given `Class`.
      */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ForecastDetailsViewModel::class.java)){
            return ForecastDetailsViewModel(args) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}

class ForecastDetailsViewModel(args : ForecastDetailsFragmentArgs) : ViewModel() {
    private val _viewState: MutableLiveData<ForecastDetailsViewState> = MutableLiveData()
    val viewState: LiveData<ForecastDetailsViewState> = _viewState

    init{
        _viewState.value = ForecastDetailsViewState(
            temp = args.temp,
            description = args.description
//            date = DATE_FORMAT.format(Date("20-06-2020")),
//            iconUrl = "http://openweathermap.org/img/wn/01d@2x.png"
        )
    }

}

