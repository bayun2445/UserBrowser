package com.example.userbrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val listUser: List<UserItem?>?):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var clicked: ItemCLicked

    interface ItemCLicked {
        fun click(position: Int)
    }

    fun setClicked(clicked: ItemCLicked){
        this.clicked = clicked
    }

    class UserViewHolder(view: View, itemClick: ItemCLicked):  RecyclerView.ViewHolder(view) {
        val civUserAvatar: ImageView = view.findViewById(R.id.civ_user_avatar)
        val tvUsername: TextView = view.findViewById(R.id.tv_username)

        init {
            view.setOnClickListener {
                itemClick.click(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_user, parent, false)

        return UserViewHolder(view, clicked)
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