package com.example.trmubaiwa.blueprint.Activities

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.trmubaiwa.blueprint.Adapters.UserListRecyclerAdapter
import com.example.trmubaiwa.blueprint.Models.UserModel
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Rooms.UserEntity
import com.example.trmubaiwa.blueprint.Utilities.EXTRA_USERDETAILS
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_users_list.*
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

        featchListAndSave()
    }

    private fun featchListAndSave() {
        userViewModel.getUsers().observe(this, Observer {
            /**Save to database*/
            var userList = mutableListOf<UserEntity>()

            if (it != null) {
                it.mapTo(userList) {
                    UserEntity(it.id, it.name, it.phone, it.username, it.company.name, it.email, it.address.city, it.website
                    )
                }
                userViewModel.setUsers(userList)
            }
            setAdapterWithList(it)

        })
    }

    private fun setAdapterWithList(it: List<UserModel>?) {
        adapter = UserListRecyclerAdapter(this, it!!) {
            val detailsIntent = Intent(this, UserDetailActivity::class.java)
            detailsIntent.putExtra(EXTRA_USERDETAILS, it.id.toString())
            startActivity(detailsIntent)
        }
        userListView.adapter = adapter
        userListView.layoutManager = layoutManager
        userListView.setHasFixedSize(true)
    }

    fun feachListFromDatabase(){

    }

    override fun onResume() {
        super.onResume()

    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwokInfo = connectivityManager.activeNetworkInfo
        return activeNetwokInfo != null && activeNetwokInfo.isConnectedOrConnecting

    }
}
