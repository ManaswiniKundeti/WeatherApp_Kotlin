package com.manu.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class DailyForecastViewHolder(
    view : View,
    private val tempDisplaySettingManager: TempDisplaySettingManager
    )
    : RecyclerView.ViewHolder(view){

    private val tempText :TextView = view.findViewById(R.id.tempText)

    private val descriptionText :TextView = view.findViewById(R.id.descriptionText)

    fun bind(dailyForecast : DailyForecast){
        tempText.text = dailyForecast.temp.formatTempForDisplay(tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = dailyForecast.description
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