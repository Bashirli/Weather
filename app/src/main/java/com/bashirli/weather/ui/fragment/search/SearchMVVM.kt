package com.bashirli.weather.ui.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashirli.weather.Resource
import com.bashirli.weather.model.local.SavedCity
import com.bashirli.weather.model.search.SearchResponse
import com.bashirli.weather.repo.local.RoomRepo
import com.bashirli.weather.repo.remote.ApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMVVM @Inject constructor(
    private val repo:ApiRepo,
    private val roomRepo: RoomRepo
) : ViewModel() {

    private val _loading=MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> get()=_loading

    private val _searchData=MutableLiveData<Resource<SearchResponse>?>()
    val searchData:LiveData<Resource<SearchResponse>?> get()=_searchData

    fun searchCity(city:String){
        _loading.value=true
        _searchData.value=null
        viewModelScope.launch {
            val response=repo.getSearchedCity(city)
            _searchData.value=response
            _loading.value=false
        }
    }

    fun addCity(savedCity: SavedCity){
        viewModelScope.launch {
            roomRepo.insertSavedCity(savedCity)
        }
    }

}