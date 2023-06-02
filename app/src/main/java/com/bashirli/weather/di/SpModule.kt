package com.bashirli.weather.di

import android.content.Context
import android.content.SharedPreferences
import com.bashirli.weather.model.token.CityTokenManager
import com.bashirli.weather.util.AppSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object SpModule {

    @Provides
    @Singleton
    fun injectSP(@ApplicationContext context:Context)=
        context.getSharedPreferences(AppSharedPreferences.CITY_MANAGER,Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun injectAppSP(sharedPreferences: SharedPreferences)=AppSharedPreferences(sharedPreferences)

    @Singleton
    @Provides
    fun injectCityTokenManager(appSharedPreferences: AppSharedPreferences)=CityTokenManager(appSharedPreferences)


}