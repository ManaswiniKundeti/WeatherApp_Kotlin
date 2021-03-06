package com.manu.weatherapp.details

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.manu.weatherapp.R
import com.manu.weatherapp.TempDisplaySettingManager
import com.manu.weatherapp.databinding.FragmentForecastDetailsBinding
import com.manu.weatherapp.formatTempForDisplay
import java.text.SimpleDateFormat
import java.util.*



class  ForecastDetailsFragment : Fragment() {

    private val args:ForecastDetailsFragmentArgs by navArgs()

    private lateinit var viewModelFactory: ForecastDetailsViewModelFactory
    private val viewModel: ForecastDetailsViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: FragmentForecastDetailsBinding? = null
    //this prop only valid between onCreate view and onDestroy view
    private val binding get() = _binding!!

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentForecastDetailsBinding.inflate(inflater, container, false)
        viewModelFactory = ForecastDetailsViewModelFactory(args)
        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewStateObserver = Observer<ForecastDetailsViewState>{ viewState ->
            binding.tempText.text = viewState.temp.formatTempForDisplay(tempDisplaySettingManager.getTempDisplaySetting())
            binding.descriptionText.text = viewState.description
//            binding.dateText.text =  viewState.date
//            binding.forecastIcon.load(viewState.iconUrl)
        }
        viewModel.viewState.observe(viewLifecycleOwner, viewStateObserver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
