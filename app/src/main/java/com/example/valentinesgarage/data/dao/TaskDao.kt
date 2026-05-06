package com.example.map-assignment.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.valentinesgarage.data.entity.TaskEntity

@Dao
interface TaskDao{
    @Insert
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE vehicleId = :vehicleId")
    fun getTasksForVehicle(vehicleId: Int): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE assignedToUserId = :userId AND isCompleted = 1")
    fun getCompletedTasksByUser(userId: Int): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE isCompleted = 1")
    fun getAllCompletedTasks(): LiveData<List<TaskEntity>>

}