package com.bashirli.weather.service.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bashirli.weather.model.local.SavedCity

@Dao
interface RoomDAO {

    @Insert
    suspend fun insertSavedCity(savedCity: SavedCity)

    @Query("SELECT * From savedcity")
    suspend fun getAllData():List<SavedCity>

    @Delete
    suspend fun deleteSavedCity(savedCity: SavedCity)

}