package com.example.trmubaiwa.blueprint.Repositories

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData

import com.example.trmubaiwa.blueprint.Models.UserModel
import com.example.trmubaiwa.blueprint.Services.Webservice


class UserRepository constructor(private val webservice: Webservice) {

    fun getUsers(): LiveData<UserModel> {
        val data: MutableLiveData<UserModel> = MutableLiveData()
        webservice.getUser()
        return data
    }


}