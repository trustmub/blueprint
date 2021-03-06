package com.example.trmubaiwa.blueprint.Services

import com.example.trmubaiwa.blueprint.Models.UserModel
import retrofit2.Call
import retrofit2.http.GET

interface Webservice {

    @GET("/users")
    fun getUser(): Call<List<UserModel>>
}