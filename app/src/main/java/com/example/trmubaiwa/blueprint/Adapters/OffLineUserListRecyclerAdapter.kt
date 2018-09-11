package com.example.trmubaiwa.blueprint.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trmubaiwa.blueprint.Models.UserModel
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Rooms.UserEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.user_list_layout.*


class OffLineUserListRecyclerAdapter(val context: Context, private val userList: List<UserEntity>, private val itemClick: (UserEntity) -> Unit)
    : RecyclerView.Adapter<OffLineUserListRecyclerAdapter.OffLineHolder>() {

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

    inner class OffLineHolder(override val containerView: View?, itemClick: (UserEntity) -> Unit)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private fun getFirstLetter(name: String): String {
            return name[0].toString().toUpperCase()
        }

        fun bindUser(user: UserEntity, context: Context) {
            tv_name?.text = user.name
            tv_list_leter_label.text = getFirstLetter(user.name)
            /** set the click listener after declaring the item click lambda on the UserListRecyclerAdapter class */
            containerView?.setOnClickListener { itemClick(user) }

        }
    }
}