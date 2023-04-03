package com.example.userbrowser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.userbrowser.api.UserItem
import com.example.userbrowser.databinding.ItemListUserBinding

class UserAdapter(private val listUser: List<UserItem?>?):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var clicked: ItemCLicked

    interface ItemCLicked {
        fun click(position: Int)
    }

    fun setClicked(clicked: ItemCLicked){
        this.clicked = clicked
    }

    inner class UserViewHolder(private val binding: ItemListUserBinding, itemClick: ItemCLicked):  RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClick.click(absoluteAdapterPosition)
            }
        }

        fun bind(user: UserItem) {
            binding.tvUsername.text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .into(binding.civUserAvatar)
        }
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
        val user = listUser!![position]
        holder.bind(user!!)
    }

    override fun getItemCount(): Int {
        return listUser!!.size
    }
}