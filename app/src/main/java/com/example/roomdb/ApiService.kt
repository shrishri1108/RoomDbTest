package com.example.roomdb

import com.example.roomdb.data.user_api.UserDetailsResponse
import com.example.roomdb.data.user_list_api.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET(KeysConstant.USER_LIST)
    fun callUsersApi(): Call<UserResponse>

    @GET("users/{user_id}")
    fun callUserDetailsApi(@Path("user_id") user_id: String): Call<UserDetailsResponse>
}
