package com.bashirli.weather.service.remote

import com.bashirli.weather.FORECAST
import com.bashirli.weather.SEARCH
import com.bashirli.weather.model.search.SearchResponse
import com.bashirli.weather.model.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET(FORECAST)
    suspend fun getForecastWeather(@Query("q") city:String,@Query("days") days:Int=7):Response<WeatherResponse>

    @GET(SEARCH)
    suspend fun getSearchedCity(@Query("q") city:String):Response<SearchResponse>
}