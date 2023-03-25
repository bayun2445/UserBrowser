package com.example.userbrowser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _userDetail = MutableLiveData<ResponseDetail?>()
    private val _isLoading = MutableLiveData<Boolean?>()

    val userDetail: LiveData<ResponseDetail?> = _userDetail
    val isLoading: LiveData<Boolean?> = _isLoading

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }

    fun getUserDetail(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username!!)
        client.enqueue(object: Callback<ResponseDetail> {
            override fun onResponse(
                call: Call<ResponseDetail>,
                response: Response<ResponseDetail>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}