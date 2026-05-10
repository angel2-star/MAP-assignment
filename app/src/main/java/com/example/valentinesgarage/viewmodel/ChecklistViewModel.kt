package com.example.valentinesgarage.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.valentinesgarage.data.AppDatabase
import com.example.valentinesgarage.data.entity.TaskEntity
import kotlinx.coroutines.launch

class ChecklistViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao = AppDatabase.getInstance(application).taskDao()

    fun getTasksForVehicle(vehicleId: Int): LiveData<List<TaskEntity>> {
        return taskDao.getTasksForVehicle(vehicleId)
    }

    fun addTask(vehicleId: Int, title: String, assignedToUserId: Int) {
        if (title.isBlank()) return
        viewModelScope.launch {
            taskDao.insert(
                TaskEntity(
                    vehicleId = vehicleId,
                    title = title.trim(),
                    assignedToUserId = assignedToUserId
                )
            )
        }
    }

    fun updateTask(task: TaskEntity, isCompleted: Boolean, notes: String) {
        viewModelScope.launch {
            taskDao.update(
                task.copy(
                    isCompleted = isCompleted,
                    notes = notes,
                    completedAt = if (isCompleted) System.currentTimeMillis() else null
                )
            )
        }
    }
}