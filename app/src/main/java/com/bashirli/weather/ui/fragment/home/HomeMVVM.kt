package com.bashirli.weather.ui.fragment.home

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
class HomeMVVM @Inject constructor (private val repo: ApiRepo) :ViewModel() {

    private val _data=MutableLiveData<Resource<WeatherResponse>?>()
    val data:LiveData<Resource<WeatherResponse>?> get()=_data

    private val _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get()=_loading

    fun getData(city:String){
        _data.value=null
        _loading.value=true
        viewModelScope.launch {
            val response=repo.getForecastWeather(city)
            _data.value=response
            _loading.value=false
        }
    }

}