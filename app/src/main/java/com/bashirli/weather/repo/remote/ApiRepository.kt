package com.bashirli.weather.repo.remote

import com.bashirli.weather.ERROR_JSON
import com.bashirli.weather.MESSAGE_JSON
import com.bashirli.weather.Resource
import com.bashirli.weather.model.search.SearchResponse
import com.bashirli.weather.model.weather.WeatherResponse
import com.bashirli.weather.service.remote.Service
import org.json.JSONObject
import javax.inject.Inject

class ApiRepository @Inject constructor(private val service: Service) : ApiRepo {
    override suspend fun getForecastWeather(city: String) : Resource<WeatherResponse> {
        return try {
            val response=service.getForecastWeather(city)
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                val errorJson=JSONObject(response.errorBody()!!.charStream().readText())
                val errorMessage=errorJson.getJSONObject(ERROR_JSON).getString(MESSAGE_JSON)
                Resource.error(errorMessage,null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

    override suspend fun getSearchedCity(city: String): Resource<SearchResponse> {
        return try{
            val response=service.getSearchedCity(city)
            if(response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?:Resource.error("Error",null)
            }else{
                val errorJson=JSONObject(response.errorBody()!!.charStream().readText())
                val errorMessage=errorJson.getJSONObject(ERROR_JSON).getString(MESSAGE_JSON)
                Resource.error(errorMessage,null)
            }
        }catch (e:Exception){
            Resource.error(e.localizedMessage,null)
        }
    }

}