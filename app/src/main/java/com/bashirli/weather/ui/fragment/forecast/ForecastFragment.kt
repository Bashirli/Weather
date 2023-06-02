package com.bashirli.weather.ui.fragment.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bashirli.weather.CustomProgressBar
import com.bashirli.weather.R
import com.bashirli.weather.Status
import com.bashirli.weather.base.BaseFragment
import com.bashirli.weather.databinding.FragmentForecastBinding
import com.bashirli.weather.model.weather.WeatherResponse
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastFragment : BaseFragment<FragmentForecastBinding>(FragmentForecastBinding::inflate) {

    private val args:ForecastFragmentArgs by navArgs()
    private val adapter=ForecastAdapter()
    private lateinit var cityToken:String
    private val viewModel : ForecastMVVM by viewModels<ForecastMVVM>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        observeData()
    }

    private fun setup(){
        cityToken=args.cityData
        if(this::cityToken.isInitialized){
            viewModel.getData(cityToken)
        }else{
            val dialog= MaterialAlertDialogBuilder(requireContext())
            dialog.setCancelable(false).setTitle(R.string.attention)
                .setMessage(R.string.smthWrong)
                .setPositiveButton(R.string.ok){dialog,which->
                   findNavController().popBackStack()
                }.create()
            dialog.show()
            return
        }

        with(binding){
            rvForecast.layoutManager=GridLayoutManager(requireContext(),2)
            rvForecast.adapter=adapter

            buttonGoBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }

    private fun observeData(){
        val progressDialog= CustomProgressBar.progressDialog(requireContext())
        with(viewModel){
            loading.observe(viewLifecycleOwner){
                if(it){
                    progressDialog.show()
                }
            }
            weatherResponse.observe(viewLifecycleOwner){
                it?.let {
                    progressDialog.cancel()
                    when(it.status){
                        Status.SUCCESS->{
                            it.data?.let {
                                setData(it)
                            }
                        }
                        Status.ERROR->{
                            Toast.makeText(requireContext(),it.message?:"Error",
                                Toast.LENGTH_SHORT).show()
                        }
                        else->{it.message?.let {message->
                            Toast.makeText(requireContext(),message,
                               Toast.LENGTH_SHORT).show()
                        }}
                    }
                }
            }
        }
    }

    private fun setData(data: WeatherResponse){
        val forecastList=data.forecast.forecastday
        adapter.updateList(forecastList)
    }


}