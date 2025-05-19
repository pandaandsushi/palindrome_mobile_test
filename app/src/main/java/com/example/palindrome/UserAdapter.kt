package com.example.palindrome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.palindrome.R
import com.example.palindrome.model.User

class UserAdapter(
    private val users: List<User>,
    private val listener: OnUserClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    interface OnUserClickListener {
        fun onUserClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = users.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onUserClick(users[position])
                }
            }
        }

        fun bind(user: User) {
            nameTextView.text = "${user.first_name} ${user.last_name}"
            emailTextView.text = user.email

            Glide.with(itemView.context)
                .load(user.avatar)
                .circleCrop()
                .into(avatarImageView)
        }
    }
}