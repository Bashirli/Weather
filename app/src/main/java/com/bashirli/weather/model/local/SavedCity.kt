package com.bashirli.weather.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedCity")
data class SavedCity(
    @ColumnInfo("cityLat")
    val cityLat:Double,
    @ColumnInfo("cityLong")
    val cityLong:Double,
    @ColumnInfo("cityRegion")
    val cityRegion:String,
    @ColumnInfo("country")
    val country:String,
    @ColumnInfo("cityName")
    val cityName:String,
    @PrimaryKey(autoGenerate = true) val id:Int=0
    )
