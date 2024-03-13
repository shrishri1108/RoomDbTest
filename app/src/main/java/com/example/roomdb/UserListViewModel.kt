package com.example.roomdb

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdb.data.user_list_api.UserResponse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListViewModel : ViewModel() {

    private var dataReceivedFormApi: Boolean = false
    private val userResponseObj = MutableLiveData<UserResponse>()
    val userResponse: LiveData<UserResponse> get() = userResponseObj
    suspend fun getUserListFromApi() {
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

    suspend fun getUserListFromRoom() {
        MyApp.database.userDao().getAllUsers()
    }

    suspend fun getUserList() {
        if (dataReceivedFormApi) {
            coroutineScope {
                getUserListFromRoom()
            }
        }

        else {
            coroutineScope {
                getUserListFromApi()
            }
        }
    }


}