package com.bashirli.weather.service.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bashirli.weather.model.local.SavedCity

@Database(entities = [SavedCity::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun getDao(): RoomDAO
}