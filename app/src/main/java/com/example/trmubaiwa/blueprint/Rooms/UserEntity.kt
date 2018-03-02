package com.example.trmubaiwa.blueprint.Rooms

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "gender") val gender: String,
        @ColumnInfo(name = "initials") val initials: String,
        @ColumnInfo(name = "surname") val surname: String,
        @ColumnInfo(name = "dateOfBirth") val dateOfBirth: String,
        @ColumnInfo(name = "clientNumber") val clientNumber: String,
        @ColumnInfo(name = "id") val id: String,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "age") val age: Int
)
