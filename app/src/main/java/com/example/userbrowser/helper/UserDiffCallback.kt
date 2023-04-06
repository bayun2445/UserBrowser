package com.example.userbrowser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.userbrowser.database.User

class UserDiffCallback(private val mOldList: List<User>, private val mNewList: List<User>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition] == mNewList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = mOldList[oldItemPosition]
        val newUser = mNewList[newItemPosition]

        return oldUser.username == newUser.username
    }
}