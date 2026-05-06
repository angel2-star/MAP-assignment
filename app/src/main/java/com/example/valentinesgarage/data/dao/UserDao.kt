package com.example.map-assignment.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.map-assignment.data.entity.UserEntity

@Dao
interface UserDao{

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun login(email: String): UserEntity

    @Query("SELECT * FROM users")
    suspend fun getAllUsers():List<UserEntity>
}