package com.example.userbrowser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.userbrowser.database.User
import com.example.userbrowser.repository.UserRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val mUserRepository = UserRepository(application)

    fun getAllFavoriteUser(): LiveData<List<User>> {
        return mUserRepository.getAllFavoriteUser()
    }
}