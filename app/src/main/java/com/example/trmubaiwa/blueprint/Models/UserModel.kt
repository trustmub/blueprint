package com.example.trmubaiwa.blueprint.Models

import com.squareup.moshi.Json

data class UserModel(@Json(name = "gender") val gender: String,
                     @Json(name = "initials") val initials: String,
                     @Json(name = "surname") val surname: String,
                     @Json(name = "dateOfBirth") val dateOfBirth: String,
                     @Json(name = "clientNumber") val clientNumber: String,
                     @Json(name = "id") val id: String,
                     @Json(name = "title") val title: String,
                     @Json(name = "age") val age: Int)