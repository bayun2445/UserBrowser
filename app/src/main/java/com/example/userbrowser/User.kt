package com.example.userbrowser

import android.os.Parcel
import android.os.Parcelable

data class User(
    var username: String? = null,
    var avatarUrl: String? = null,
    var homeUrl: String? = null,
    var detailUrl: String? = null,
    var followersUrl: String? = null,
    var followingUrl: String? = null,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(avatarUrl)
        parcel.writeString(homeUrl)
        parcel.writeString(detailUrl)
        parcel.writeString(followersUrl)
        parcel.writeString(followingUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
