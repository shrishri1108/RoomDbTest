package com.example.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomdb.Dao.UserDao
import com.example.roomdb.Dao.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
