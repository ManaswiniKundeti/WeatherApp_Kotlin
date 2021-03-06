package com.manu.weatherapp.location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.manu.weatherapp.Location
import com.manu.weatherapp.LocationRepository
import com.manu.weatherapp.R

class LocationEntryFragment : Fragment() {

    private lateinit var locationRepository: LocationRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

        locationRepository = LocationRepository(requireContext())

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_location_entry, container, false)

        val zipcodeEditText : EditText = view.findViewById(R.id.zipcodeEditText)

        //Used to navigate to current forecast frag
        val enterButton : Button = view.findViewById(R.id.enterButton)
        enterButton.setOnClickListener {
            val zipcode :String = zipcodeEditText.text.toString()
            if(zipcode.length != 5){
                Toast.makeText(requireContext(), R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            }else{
                //any time we enter a zipcode, we store it in our locationRepository
                locationRepository.saveLocation(Location.Zipcode(zipcode))
                //navigateUp, just goes back to what was there previously
                findNavController().navigateUp()
            }
        }

        return view
    }


}
