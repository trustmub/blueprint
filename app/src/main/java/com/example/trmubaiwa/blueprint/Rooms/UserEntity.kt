package com.example.trmubaiwa.blueprint.Rooms

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "phone") val phone: String,
        @ColumnInfo(name = "username") val username: String,
        @ColumnInfo(name = "company") val company: String,
        @ColumnInfo(name = "email") val email: String,
        @ColumnInfo(name = "city") val city: String,
        @ColumnInfo(name = "website") val website: String
)
