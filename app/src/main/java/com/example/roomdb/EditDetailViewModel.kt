package com.example.roomdb

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomdb.data.user_api.UserDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditDetailViewModel : ViewModel() {

    private val userDetailsResponseObj = MutableLiveData<UserDetailsResponse>()
    val userDetailsResponse : LiveData<UserDetailsResponse> get() = userDetailsResponseObj
    fun updateUserDetails(userId: String) {
        ApiClient.getInstance()!!.callUserDetailsApi(userId).enqueue(object :
            Callback<UserDetailsResponse> {
            override fun onResponse(call: Call<UserDetailsResponse>, response: Response<UserDetailsResponse>) {
                if (response.isSuccessful) {
                    userDetailsResponseObj.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                Log.e(KeysConstant.TAG, "onFailure: ", t)
            }
        })
    }


}