package com.example.map-assignment.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.valentinesgarage.data.dao.TaskDao
import com.example.valentinesgarage.data.dao.UserDao
import com.example.valentinesgarage.data.dao.VehicleDao
import com.example.valentinesgarage.data.entity.TaskEntity
import com.example.valentinesgarage.data.entity.UserEntity
import com.example.valentinesgarage.data.entity.VehicleEntity

@Database(
    entities = [UserEntity::class, VehicleEntity::class, TaskEntity::class],
    version = 1,
    exportSchema = false
)