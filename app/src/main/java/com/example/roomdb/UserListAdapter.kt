package com.example.roomdb

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.roomdb.Dao.UserEntity
import com.example.roomdb.data.user_list_api.User
import com.example.roomdb.databinding.ItemUserBinding

class UserListAdapter(var userList: List<UserEntity>, val onClickListener: OnClickListener) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent, false)

        return UserViewHolder(itemUserBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item_pos = holder.adapterPosition
        val item =  userList[item_pos]
        item?.let {items->
            item.image?.let { Glide.with(holder.itemUserBinding.ivUser.context).load(Uri.parse(it)).into(holder.itemUserBinding.ivUser) }

            holder.itemUserBinding.tvUserName.text = items.username

            holder.itemUserBinding.tvPhoneNo.text = items.phone

            holder.itemUserBinding.cvUser.setOnClickListener { onClickListener.onUserClick(items, item_pos) }
            holder.itemUserBinding.btnEdit.setOnClickListener { onClickListener.onEditClick(items, item_pos) }
            holder.itemUserBinding.btnDelete.setOnClickListener { onClickListener.onDeleteClick(items, item_pos) }
        }
    }

    interface OnClickListener {
        fun onUserClick(user: UserEntity, position: Int)
        fun onEditClick(user: UserEntity, position: Int)
        fun onDeleteClick(user: UserEntity, position: Int)
    }
    override fun getItemCount(): Int {
       return userList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshList(newUserList: List<UserEntity>) {
        this.userList = newUserList
        notifyDataSetChanged()
    }

    class UserViewHolder(val itemUserBinding: ItemUserBinding) : RecyclerView.ViewHolder(itemUserBinding.root)

}
