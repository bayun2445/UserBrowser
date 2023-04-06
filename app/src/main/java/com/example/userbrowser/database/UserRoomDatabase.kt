package com.example.userbrowser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class UserRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: UserRoomDatabase? = null

        @JvmStatic
        fun getRoomDatabase(context: Context): UserRoomDatabase {
            if (instance == null) {
                synchronized(UserRoomDatabase::class.java){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java,
                        "user_database"
                    ).build()
                }
            }

            return instance as UserRoomDatabase
        }
    }
}