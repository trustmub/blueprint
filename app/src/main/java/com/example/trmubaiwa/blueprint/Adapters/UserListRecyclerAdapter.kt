package com.example.trmubaiwa.blueprint.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trmubaiwa.blueprint.Models.UserModel
import com.example.trmubaiwa.blueprint.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.user_list_layout.*


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

    inner class Holder(override val containerView: View?, itemClick: (UserModel) -> Unit)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private fun getFirstLetter(name: String): String {
            return name[0].toString().toUpperCase()
        }

        fun bindUser(user: UserModel, context: Context) {
            tv_name?.text = user.name
            tv_list_leter_label.text = getFirstLetter(user.name)
            /** set the click listener after declaring the item click lambda on the UserListRecyclerAdapter class */
            containerView?.setOnClickListener { itemClick(user) }

        }
    }
}