package com.example.valentinesgarage.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.valentinesgarage.data.entity.VehicleEntity

@Dao
interface VehicleDao {
    @Insert
    suspend fun insert(vehicle: VehicleEntity)

    @Query("SELECT * FROM vehicles ORDER BY checkedInAt DESC")
    fun getAllVehicles(): LiveData<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles WHERE id = :id")
    suspend fun getById(id: Int): VehicleEntity?

    @Query("SELECT * FROM vehicles WHERE plateNumber = :plateNumber")
    suspend fun getByPlateNumber(plateNumber: String): VehicleEntity?
}


