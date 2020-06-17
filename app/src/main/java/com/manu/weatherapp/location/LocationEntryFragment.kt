package com.manu.weatherapp.location


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.manu.weatherapp.AppNavigator

import com.manu.weatherapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LocationEntryFragment : Fragment() {

    //Used to go to current forecast frag
    private lateinit var appNavigator : AppNavigator
    override fun onAttach(context: Context) {  //fragment lifecycle method
        super.onAttach(context)
        appNavigator = context as AppNavigator  //'as' is used to cast a var to another type
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
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
                appNavigator.navigateToCurrentForecast(zipcode)
            }
        }

        return view
    }


}
