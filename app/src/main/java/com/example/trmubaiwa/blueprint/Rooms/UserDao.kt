package com.example.trmubaiwa.blueprint.Rooms

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.trmubaiwa.blueprint.Models.UserModel


@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun insertUser(user: UserEntity) {
    }

    @Query("SELECT * FROM users ")
    fun getUsers(): List<UserModel>

    @Delete
    fun deleteUser(user: UserEntity) {
    }

}