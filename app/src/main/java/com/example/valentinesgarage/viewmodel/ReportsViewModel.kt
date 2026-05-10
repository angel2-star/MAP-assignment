package com.example.valentinesgarage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.valentinesgarage.data.AppDatabase
import com.example.valentinesgarage.data.entity.TaskEntity
import com.example.valentinesgarage.data.entity.VehicleEntity

class ReportsViewModel(application: Application) : AndroidViewModel(application) {

    private val vehicleDao = AppDatabase.getInstance(application).vehicleDao()
    private val taskDao = AppDatabase.getInstance(application).taskDao()

    val allVehicles: LiveData<List<VehicleEntity>> = vehicleDao.getAllVehicles()
    val completedTasks: LiveData<List<TaskEntity>> = taskDao.getAllCompletedTasks()

    suspend fun getUserName(userId: Int): String {
        return AppDatabase.getInstance(getApplication())
            .userDao().getAllUsers()
            .find { it.id == userId }?.name ?: "Unknown"
    }
}