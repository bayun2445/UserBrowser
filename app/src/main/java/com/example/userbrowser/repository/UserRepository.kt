package com.example.userbrowser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.userbrowser.database.User
import com.example.userbrowser.database.UserDao
import com.example.userbrowser.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private var mUserDao: UserDao
    private var executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getRoomDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllFavoriteUser(): LiveData<List<User>> {
        return mUserDao.queryAll()
    }

    fun insert(user: User) {
        executorService.execute {
            mUserDao.insert(user)
        }
    }

    fun delete(user: User) {
        executorService.execute {
            mUserDao.delete(user)
        }
    }
}