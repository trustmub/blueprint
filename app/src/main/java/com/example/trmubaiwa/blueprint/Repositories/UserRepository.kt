package com.example.trmubaiwa.blueprint.Repositories

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import android.util.Log

import com.example.trmubaiwa.blueprint.Models.UserModel
import com.example.trmubaiwa.blueprint.Rooms.UserDao
import com.example.trmubaiwa.blueprint.Rooms.UserEntity
import com.example.trmubaiwa.blueprint.Services.Webservice
import org.w3c.dom.Entity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService


class UserRepository constructor(private val webservice: Webservice, private val userDao: UserDao, private val executorService: ExecutorService) {

    fun insertUsers(users: List<UserEntity>?) {
        executorService.execute {
            userDao.insertUsers(users)
        }
    }

    fun getUserFromDb(): LiveData<List<UserEntity>> {
        val dbUsers: MutableLiveData<List<UserEntity>> = MutableLiveData()
        executorService.execute {
            dbUsers.postValue(userDao.getUsers())
        }
        return dbUsers
    }


    fun getUsers(): LiveData<List<UserModel>> {
        val data: MutableLiveData<List<UserModel>> = MutableLiveData()
        webservice.getUser().enqueue(UsersCallback(data))
        return data
    }


    inner class UsersCallback(val data: MutableLiveData<List<UserModel>>) : Callback<List<UserModel>> {
        override fun onFailure(call: Call<List<UserModel>>?, t: Throwable?) {

        }

        override fun onResponse(call: Call<List<UserModel>>?, response: Response<List<UserModel>>?) {
            data.value = when {
                response?.code() == 200 -> { // successful response
                    response.body()
                }

                else -> {
                    val errorBody = response?.body()
                    Log.e("Network", errorBody.toString())
                    errorBody
                }
            }
        }

    }

}