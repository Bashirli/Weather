package com.bashirli.weather.repo.local

import com.bashirli.weather.model.local.SavedCity
import com.bashirli.weather.service.local.RoomDAO
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val roomDAO: RoomDAO
) : RoomRepo {
    override suspend fun insertSavedCity(savedCity: SavedCity) {
        roomDAO.insertSavedCity(savedCity)
    }

    override suspend fun getAllData(): List<SavedCity> {
        return roomDAO.getAllData()
    }

    override suspend fun deleteSavedCity(savedCity: SavedCity) {
        roomDAO.deleteSavedCity(savedCity)
    }
}