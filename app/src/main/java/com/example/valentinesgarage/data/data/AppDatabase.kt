package com.example.valentinesgarage.data

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
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun vehicleDao(): VehicleDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "valentines_garage_db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Using raw SQL here since DAOs are not safe to use inside onCreate
                            db.execSQL(
                                "INSERT INTO users (name, email, password, role) " +
                                        "VALUES ('Valentine', 'manager@garage.com', 'admin123', 'manager')"
                            )
                            db.execSQL(
                                "INSERT INTO users (name, email, password, role) " +
                                        "VALUES ('John', 'john@garage.com', 'mech123', 'mechanic')"
                            )
//                            db.execSQL(
//                                ""
//                            )
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}