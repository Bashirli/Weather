package com.bashirli.weather.util

import android.content.SharedPreferences
import javax.inject.Inject

class AppSharedPreferences @Inject constructor(
    private val sp: SharedPreferences
) {

    fun getCityToken():String?{
        return sp.getString(CITY_TOKEN,null)
    }

    fun addCity(cityToken:String){
        sp.edit().putString(CITY_TOKEN,cityToken).apply()
    }

    fun removeCity(){
        sp.edit().remove(CITY_TOKEN).apply()
    }

    companion object{
        const val CITY_MANAGER="CITY_MANAGER"
        const val CITY_TOKEN="CITY_TOKEN"
    }

}