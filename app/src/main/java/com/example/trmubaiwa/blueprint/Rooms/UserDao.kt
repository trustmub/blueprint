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

    /** replaces all the record in the database is there are duplicates
     *  and adding new ones if there is any
     */
    @Insert(onConflict = REPLACE)
    fun insertUsers(userList: List<UserEntity>?)

    @Query("SELECT * FROM users ")
    fun getUsers(): List<UserEntity>

    /** this will delete all the records in the database */
    @Query("DELETE FROM users")
    fun deleteUsers()

    /** This will delete that particular user record passed on the
     * function as an argument
     */
    @Delete
    fun deleteUser(user: UserEntity)

}