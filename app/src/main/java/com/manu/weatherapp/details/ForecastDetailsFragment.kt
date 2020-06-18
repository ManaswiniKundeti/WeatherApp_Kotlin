package com.manu.weatherapp.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.manu.weatherapp.R
import com.manu.weatherapp.TempDisplaySettingManager
import com.manu.weatherapp.formatTempForDisplay
import com.manu.weatherapp.showTempDisplaySettingDialog

class ForecastDetailsFragment : Fragment() {

    private val args:ForecastDetailsFragmentArgs by navArgs()

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val layout =  inflater.inflate(R.layout.fragment_forecast_details, container, false)

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val tempText = layout.findViewById<TextView>(R.id.tempText)
        val descriptionText = layout.findViewById<TextView>(R.id.descriptionText)

        //set the values of the available arguments(args are made available using Delegate feature
        // in kotlin, it is accessed using navArgs()) into the UI views
        tempText.text = args.temp.formatTempForDisplay(tempDisplaySettingManager.getTempDisplaySetting())
        descriptionText.text = args.description


        return layout
    }

}
