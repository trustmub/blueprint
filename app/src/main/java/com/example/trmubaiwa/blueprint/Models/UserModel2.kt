package com.example.trmubaiwa.blueprint.Models


import com.squareup.moshi.Json


data class UserModel(@Json(name = "website") val website: String,
                     @Json(name = "address") val address: Address,
                     @Json(name = "phone") val phone: String,
                     @Json(name = "name") val name: String,
                     @Json(name = "company") val company: Company,
                     @Json(name = "id") val id: Int = 0,
                     @Json(name = "email") val email: String,
                     @Json(name = "username") val username: String)

data class Company(@Json(name = "bs") val bs: String,
                   @Json(name = "catchPhrase") val catchPhrase: String,
                   @Json(name = "name") val name: String)


data class Address(@Json(name = "zipcode") val zipcode: String,
                   @Json(name = "geo") val geo: Geo,
                   @Json(name = "suite") val suite: String,
                   @Json(name = "city") val city: String,
                   @Json(name = "street") val street: String)

data class Geo(@Json(name = "lng") val lng: String,
               @Json(name = "lat") val lat: String)

