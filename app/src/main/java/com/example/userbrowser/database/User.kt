package com.example.userbrowser.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("username")
    var username: String = "",

    @ColumnInfo("avatar_url")
    var avatarUrl: String? = null,
) : Parcelable
