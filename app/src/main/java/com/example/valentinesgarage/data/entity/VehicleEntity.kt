package com.example.valentinesgarage.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val plateNumber: String,
    val ownerName: String,
    val kilometersDriven: Int,
    val conditionNotes: String,
    val conditionRating: Int,
    val checkedInAt: Long = System.currentTimeMillis(),
    val checkedInByUserId: Int
)