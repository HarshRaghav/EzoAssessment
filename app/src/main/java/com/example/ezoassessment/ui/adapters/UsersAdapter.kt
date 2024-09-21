package com.example.ezoassessment.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ezoassessment.data.models.User
import com.example.ezoassessment.databinding.ItemUserBinding

class UsersAdapter(var userList: List<User>)  : RecyclerView.Adapter<UsersAdapter.UserItemViewHolder>() {
    private  var onCLickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemViewHolder {
        return UserItemViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val name = userList[position].first_name + " " + userList[position].last_name
        val id = "id: " + userList[position].id.toString()
        holder.userName.text = name
        holder.userEmail.text = userList[position].email
        holder.userId.text = id
        Glide.with(holder.itemView).load(userList[position].avatar).into(holder.userImage)

        holder.itemView.setOnClickListener {
            onCLickListener?.onClick(position, userList[position])
        }
    }

    fun setOnClickListener(listener: OnClickListener?){
        this.onCLickListener = listener
    }

    fun updateData(list: List<User>) {
        userList = list
        notifyDataSetChanged()
    }

    fun clear() {
        userList = emptyList()
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(position: Int, model: User)
    }

    inner class UserItemViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        val userImage: ImageView = binding.userImage
        val userName: TextView = binding.userName
        val userEmail: TextView = binding.userEmail
        val userId: TextView = binding.userId
    }
}