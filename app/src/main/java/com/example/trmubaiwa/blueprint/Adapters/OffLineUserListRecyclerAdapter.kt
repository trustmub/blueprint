package com.example.trmubaiwa.blueprint.Adapters

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Rooms.UserEntity


class OffLineUserListRecyclerAdapter(val context: Context, private val userList: List<UserEntity>, private val itemClick: (UserEntity) -> Unit) : RecyclerView.Adapter<OffLineUserListRecyclerAdapter.OffLineHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OffLineHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_list_layout, parent, false)

        return OffLineHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return userList.count()
    }

    override fun onBindViewHolder(holder: OffLineHolder?, position: Int) {
        holder?.bindUser(userList[position], context)
    }

    inner class OffLineHolder(itemView: View?, itemClick: (UserEntity) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val userFullName = itemView?.findViewById<TextView>(R.id.nameTextView)

        fun bindUser(user: UserEntity, context: Context) {
            userFullName?.text = user.name
            /** set the click listener after declaring the item click lambda on the UserListRecyclerAdapter class */
            itemView.setOnClickListener { itemClick(user) }

        }
    }
}