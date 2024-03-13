package com.example.roomdb

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomdb.data.user_list_api.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListViewModel: ViewModel() {

    private val userResponseObj = MutableLiveData<UserResponse>()
    val userResponse : LiveData<UserResponse> get() = userResponseObj
    fun getUserList() {
        ApiClient.getInstance()!!.callUsersApi().enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    userResponseObj.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(KeysConstant.TAG, "onFailure: ", t)
            }
        })
    }


}