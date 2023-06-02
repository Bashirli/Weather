package com.bashirli.weather.model.token

import com.bashirli.weather.util.AppSharedPreferences
import javax.inject.Inject

class CityTokenManager @Inject constructor(private val sp:AppSharedPreferences) {

    fun getCityToken():String?{
        return sp.getCityToken()
    }

    fun removeToken(){
        sp.removeCity()
    }

    fun addCityToken(cityToken:String){
        sp.addCity(cityToken)
    }

}