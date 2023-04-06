package com.example.userbrowser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userbrowser.database.User
import com.example.userbrowser.databinding.ItemListUserBinding
import com.example.userbrowser.helper.UserDiffCallback

class UserAdapter:
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var clicked: ItemCLicked
    private var listUser = ArrayList<User>()

    interface ItemCLicked {
        fun click(position: Int)
    }

    fun setListUser(listUser: List<User>) {
        val diffCallback = UserDiffCallback(this.listUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listUser.clear()
        this.listUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setClicked(clicked: ItemCLicked){
        this.clicked = clicked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemBinding = ItemListUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(itemBinding, clicked)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    inner class UserViewHolder(private val binding: ItemListUserBinding, itemClick: ItemCLicked):  RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClick.click(absoluteAdapterPosition)
            }
        }

        fun bind(user: User) {
            binding.tvUsername.text = user.username
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.civUserAvatar)
        }
    }
}