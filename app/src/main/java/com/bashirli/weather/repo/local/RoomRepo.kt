package com.bashirli.weather.repo.local

import com.bashirli.weather.model.local.SavedCity

interface RoomRepo {

    suspend fun insertSavedCity(savedCity: SavedCity)

    suspend fun getAllData():List<SavedCity>

    suspend fun deleteSavedCity(savedCity: SavedCity)
}