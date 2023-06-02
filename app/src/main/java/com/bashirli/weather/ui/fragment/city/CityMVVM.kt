package com.bashirli.weather.ui.fragment.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.weather.model.local.SavedCity
import com.bashirli.weather.repo.local.RoomRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityMVVM @Inject constructor(
    private val repo:RoomRepo,
    private val roomRepo:RoomRepo
) : ViewModel(){

    private val _cityData=MutableLiveData<List<SavedCity>>()
    val cityData:LiveData<List<SavedCity>> get()=_cityData

    fun getData(){
        viewModelScope.launch {
            val response=repo.getAllData()
            _cityData.value=response
        }
    }

    fun insertData(savedCity: SavedCity){
        viewModelScope.launch {
            roomRepo.insertSavedCity(savedCity)
        }
    }

    fun deleteCity(savedCity: SavedCity){
        viewModelScope.launch {
            roomRepo.deleteSavedCity(savedCity)
        }
    }


}