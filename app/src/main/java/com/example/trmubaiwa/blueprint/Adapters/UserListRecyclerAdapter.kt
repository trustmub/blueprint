package com.example.trmubaiwa.blueprint.Adapters

import android.arch.lifecycle.LiveData
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.trmubaiwa.blueprint.Models.UserModel
import com.example.trmubaiwa.blueprint.R
import kotlinx.android.synthetic.main.user_list_layout.view.*
import java.util.*

/**
 * Created by trmubaiwa on 2018/03/10.
 */
class UserListRecyclerAdapter(val context: Context, private val userList: List<UserModel>, private val itemClick: (UserModel) -> Unit) : RecyclerView.Adapter<UserListRecyclerAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_list_layout, parent, false)

        return Holder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return userList.count()
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bindUser(userList[position], context)
    }

    inner class Holder(itemView: View?, itemClick: (UserModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val userFullName = itemView?.findViewById<TextView>(R.id.nameTextView)

        fun bindUser(user: UserModel, context: Context) {
            userFullName?.text = user.name
            /** set the click listener after declaring the item click lambda on the UserListRecyclerAdapter class */
            itemView.setOnClickListener { itemClick(user) }

        }
    }
}