package com.example.trmubaiwa.blueprint.Activities

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.trmubaiwa.blueprint.Adapters.UserListRecyclerAdapter
import com.example.trmubaiwa.blueprint.Models.UserModel
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Utilities.EXTRA_USERDETAILS
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_users_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.koin.android.ext.android.inject

class UsersListActivity : AppCompatActivity() {

    private lateinit var adapter: UserListRecyclerAdapter
    private val userViewModel by inject<UserViewModel>()
    private lateinit var userList: Array<UserModel>
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        layoutManager = LinearLayoutManager(this)

        userViewModel.getUsers().observe(this, Observer {
            adapter = UserListRecyclerAdapter(this, it!!){
                val detailsIntent = Intent(this, UserDetailActivity::class.java)
                detailsIntent.putExtra(EXTRA_USERDETAILS, it.id.toString())
                startActivity(detailsIntent)
            }
            userListView.adapter = adapter
            userListView.layoutManager = layoutManager
            userListView.setHasFixedSize(true)

        })
    }
}
