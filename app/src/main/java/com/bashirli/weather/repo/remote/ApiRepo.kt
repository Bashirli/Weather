package com.bashirli.weather.repo.remote

import com.bashirli.weather.Resource
import com.bashirli.weather.model.search.SearchResponse
import com.bashirli.weather.model.weather.WeatherResponse

interface ApiRepo {

    suspend fun getForecastWeather(city:String) : Resource<WeatherResponse>

    suspend fun getSearchedCity(city:String) : Resource<SearchResponse>


}