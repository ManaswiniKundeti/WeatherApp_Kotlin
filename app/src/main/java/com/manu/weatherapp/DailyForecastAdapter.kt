package com.manu.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.manu.weatherapp.api.DailyForecast
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyy")

class DailyForecastViewHolder(
    view : View,
    private val tempDisplaySettingManager: TempDisplaySettingManager
    )
    : RecyclerView.ViewHolder(view){

    private val tempText :TextView = view.findViewById(R.id.tempText)

    private val descriptionText :TextView = view.findViewById(R.id.descriptionText)

    private val dateText : TextView = view.findViewById(R.id.dateText)

    private val forecastIcon = view.findViewById<ImageView>(R.id.forecastIcon)

    fun bind(dailyForecast : DailyForecast){
        tempText.text = dailyForecast.temp.max.formatTempForDisplay(tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = dailyForecast.weather[0].description
        dateText.text = DATE_FORMAT.format(Date(dailyForecast.date * 1000))

        //load image into this imageview :url :http://openweathermap.org/img/wn/10d@2x.png
        val iconId = dailyForecast.weather[0].icon
        forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@2x.png")
    }

}

class DailyForecastAdapter(
    private val tempDisplaySettingManager: TempDisplaySettingManager,
    private val clickHandler : (DailyForecast) -> Unit
    )
    : ListAdapter<DailyForecast, DailyForecastViewHolder>(DIFF_CONFIG) {

    companion object{
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<DailyForecast>(){

            /**
             * Called to check whether two objects represent the same item.
             */
            override fun areItemsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem === newItem   // === tells if they the exact same object reference
            }

            /**
             * Called to check whether two items have the same data.
             */
            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem == newItem //tells if the contents are the same but not essentially have the same object reference
            }

        }
    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context).
            inflate(R.layout.item_daily_forecast, parent, false)

        return DailyForecastViewHolder(itemView, tempDisplaySettingManager)
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * */
    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            clickHandler(getItem(position))
        }
    }

}