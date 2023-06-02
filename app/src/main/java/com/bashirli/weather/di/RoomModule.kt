package com.bashirli.weather.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bashirli.weather.repo.local.RoomRepo
import com.bashirli.weather.repo.local.RoomRepository
import com.bashirli.weather.service.local.RoomDAO
import com.bashirli.weather.service.local.RoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Provides
    @Singleton
    fun injectRoomDB(@ApplicationContext context:Context)=
        Room.databaseBuilder(
        context,
            RoomDB::class.java,
            "cities"
    ).build()


    @Provides
    @Singleton
    fun injectRoomDAO(roomDB: RoomDB)=roomDB.getDao()

    @Singleton
    @Provides
    fun injectRoomRepo(roomDAO: RoomDAO)=RoomRepository(roomDAO) as RoomRepo

}