package com.bashirli.weather.ui.fragment.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import com.bashirli.weather.CustomProgressBar
import com.bashirli.weather.R
import com.bashirli.weather.Status
import com.bashirli.weather.base.BaseFragment
import com.bashirli.weather.databinding.FragmentHomeBinding
import com.bashirli.weather.model.weather.Hour
import com.bashirli.weather.model.weather.WeatherResponse
import com.bashirli.weather.ui.fragment.forecast.ForecastAdapter
import com.bashirli.weather.ui.fragment.home.adapter.HourAdapter
import com.bashirli.weather.ui.fragment.home.adapter.ShowForecastAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel:HomeMVVM by viewModels<HomeMVVM>()
    private val args:HomeFragmentArgs by navArgs()
    private val hourAdapter=HourAdapter()
    private val forecastAdapter= ShowForecastAdapter()
    private lateinit var city:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        observeData()
    }

    private fun setup(){
        city=args.cityToken
        if(this::city.isInitialized){
            viewModel.getData(city)
        }else{
            val dialog=MaterialAlertDialogBuilder(requireContext())
            dialog.setCancelable(false).setTitle(R.string.attention)
                .setMessage(R.string.smthWrong)
                .setPositiveButton(R.string.ok){dialog,which->
                    requireActivity().finish()
                }.create()
            dialog.show()
            return
        }

        with(binding){
            rvHourly.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            rvHourly.adapter=hourAdapter

            rvForecast.layoutManager=LinearLayoutManager(requireContext())
            rvForecast.adapter=forecastAdapter

            imageAdd.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCityFragment())
            }

            button7days.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToForecastFragment(city))
            }
        }

    }

    private fun observeData(){
        val progressDialog=CustomProgressBar.progressDialog(requireContext())
        with(viewModel){
           loading.observe(viewLifecycleOwner){
               if(it){
                   progressDialog.show()
               }
           }
            data.observe(viewLifecycleOwner){
                it?.let {
                    progressDialog.cancel()
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let {
                                setData(it)
                            }
                        }
                        Status.ERROR->{
                            Toast.makeText(requireContext(),it.message?:"Error",Toast.LENGTH_SHORT).show()
                        }
                        else->{it.message?.let {message->
                            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                        }}
                    }
                }
            }
        }
    }

    private fun setData(data: WeatherResponse){
        val myWeatherData=data
        val myForecast=data.forecast
        val forecastToday=data.forecast.forecastday[0]
        with(binding){
            weatherData=data
            forecastDay=forecastToday
            hourAdapter.updateList(forecastToday.hour)

            forecastAdapter.updateList(myForecast.forecastday.subList(0,4))

        }
    }

}