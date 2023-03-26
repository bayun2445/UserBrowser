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
    private val _followers = MutableLiveData<List<UserItem?>?>()
    private val _followings = MutableLiveData<List<UserItem?>?>()

    val userDetail: LiveData<ResponseDetail?> = _userDetail
    val isLoading: LiveData<Boolean?> = _isLoading
    val followers: LiveData<List<UserItem?>?> = _followers
    val followings: LiveData<List<UserItem?>?> = _followings

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }

    fun getUserData(username: String?) {
        Thread {
            getUserDetail(username)
        }.start()

        Thread {
            getUserFollower(username)
        }.start()

        Thread {
            getUserFollowing(username)
        }.start()
    }

    private fun getUserFollowing(username: String?) {
        val client = ApiConfig.getApiService().getUserFollowing(username!!)
        client.enqueue(object: Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                if (response.isSuccessful) {
                    _followings.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun getUserFollower(username: String?) {
        val client = ApiConfig.getApiService().getUserFollower(username!!)
        client.enqueue(object: Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                if (response.isSuccessful) {
                    _followers.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    private fun getUserDetail(username: String?) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService().getUserDetail(username!!)
        client.enqueue(object: Callback<ResponseDetail> {
            override fun onResponse(
                call: Call<ResponseDetail>,
                response: Response<ResponseDetail>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _userDetail.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}