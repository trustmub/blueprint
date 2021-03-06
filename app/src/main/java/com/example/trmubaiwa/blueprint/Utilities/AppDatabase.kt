package com.example.trmubaiwa.blueprint.Utilities

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.trmubaiwa.blueprint.Rooms.UserDao
import com.example.trmubaiwa.blueprint.Rooms.UserEntity


@Database(entities = arrayOf(UserEntity::class), version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    /** add your DAO here as abstract functions */
    abstract fun userDao(): UserDao
}
