package com.example.userbrowser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.userbrowser.api.ResponseGithub
import com.example.userbrowser.api.UserItem
import com.example.userbrowser.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _listUsers = MutableLiveData<List<UserItem?>?>()
    private val _isLoading = MutableLiveData<Boolean?>()

    val listUser: LiveData<List<UserItem?>?> = _listUsers
    val isLoading: LiveData<Boolean?> = _isLoading

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    init {
        searchUser()
    }

    fun searchUser(username: String = "john") {
        _isLoading.value = true

        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object: Callback<ResponseGithub> {
            override fun onResponse(
                call: Call<ResponseGithub>,
                response: Response<ResponseGithub>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUsers.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseGithub>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}