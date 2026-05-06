package com.example.valentinesgarage.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vehicleId: Int,
    val title: String,
    val isCompleted: Boolean = false,
    val notes: String = "",
    val assignedToUserId: Int,
    val completedAt: Long? = null
)