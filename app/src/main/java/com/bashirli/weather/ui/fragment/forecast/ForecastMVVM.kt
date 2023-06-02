package com.bashirli.weather.ui.fragment.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.weather.Resource
import com.bashirli.weather.model.weather.WeatherResponse
import com.bashirli.weather.repo.remote.ApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastMVVM @Inject constructor(
    private val repo: ApiRepo
) : ViewModel() {

    private val _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get()=_loading

    private val _weatherResponse=MutableLiveData<Resource<WeatherResponse>?>()
    val weatherResponse:LiveData<Resource<WeatherResponse>?> get()=_weatherResponse


    fun getData(cityToken:String){
        _loading.value=true
        _weatherResponse.value=null
    viewModelScope.launch {
        val response=repo.getForecastWeather(cityToken)
        _weatherResponse.value=response
        _loading.value=false
    }

    }

}