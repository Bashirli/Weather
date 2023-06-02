package com.bashirli.weather.di

import com.bashirli.weather.BASE_URL
import com.bashirli.weather.repo.remote.ApiRepo
import com.bashirli.weather.repo.remote.ApiRepository
import com.bashirli.weather.service.remote.ApiKeyInterceptor
import com.bashirli.weather.service.remote.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun injectInterceptor()= ApiKeyInterceptor()

    @Singleton
    @Provides
    fun injectOkHttp(interceptor: ApiKeyInterceptor):OkHttpClient=
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Singleton
    @Provides
    fun injectRetrofit(okHttpClient: OkHttpClient)=Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()

    @Singleton
    @Provides
    fun injectService(retrofit: Retrofit)=retrofit.create(Service::class.java)

    @Singleton
    @Provides
    fun injectRepo(service: Service)= ApiRepository(service) as ApiRepo

}