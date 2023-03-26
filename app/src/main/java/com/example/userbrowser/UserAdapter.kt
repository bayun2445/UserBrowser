package com.example.userbrowser

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

    inner class UserViewHolder(binding: ItemListUserBinding, itemClick: ItemCLicked):  RecyclerView.ViewHolder(binding.root) {
        val civUserAvatar: ImageView = binding.civUserAvatar
        val tvUsername: TextView = binding.tvUsername
        init {
            binding.root.setOnClickListener {
                itemClick.click(absoluteAdapterPosition)
            }
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

        Glide.with(holder.itemView.context)
            .load(user?.avatarUrl)
            .into(holder.civUserAvatar)

        holder.tvUsername.text = user?.login
    }

    override fun getItemCount(): Int {
        return listUser!!.size
    }
}