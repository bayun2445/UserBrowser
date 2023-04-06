package com.example.userbrowser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM user")
    fun queryAll(): LiveData<List<User>>
    @Query("SELECT * FROM user WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<User>
}