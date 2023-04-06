package com.example.userbrowser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.userbrowser.api.ResponseDetail
import com.example.userbrowser.api.UserItem
import com.example.userbrowser.api.ApiConfig
import com.example.userbrowser.database.User
import com.example.userbrowser.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {
    private val _userDetail = MutableLiveData<ResponseDetail?>()
    private val _isLoading = MutableLiveData<Boolean?>()
    private val _followers = MutableLiveData<List<UserItem?>?>()
    private val _followings = MutableLiveData<List<UserItem?>?>()
    private val _isFavorite = MutableLiveData<Boolean?>()

    val userDetail: LiveData<ResponseDetail?> = _userDetail
    val isLoading: LiveData<Boolean?> = _isLoading
    val followers: LiveData<List<UserItem?>?> = _followers
    val followings: LiveData<List<UserItem?>?> = _followings
    val isFavorite: LiveData<Boolean?> = _isFavorite

    private val mUserRepository = UserRepository(application)

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

    fun setFavorite(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }

    fun checkUserIsFavorite(username: String): LiveData<User> {
        return mUserRepository.getUserByUsername(username)
    }

    fun addToFavorite(user: User) {
        mUserRepository.insert(user)
    }

    fun removeFromFavorite(user: User) {
        mUserRepository.delete(user)
    }

    companion object {
        private val TAG = DetailViewModel::class.java.simpleName
    }
}