package com.example.roomdb.Dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val user_id: String = "",
    val username: String,
    val phone: String,
    val birthDate: String,
    val email: String,
    val gender: String,
    val image: String
)