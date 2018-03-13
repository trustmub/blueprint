package com.example.trmubaiwa.blueprint.Activities

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.trmubaiwa.blueprint.Adapters.UserListRecyclerAdapter
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Utilities.EXTRA_USER_DETAILS
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_users_list.*
import org.jetbrains.anko.AnkoLogger
import org.koin.android.ext.android.inject

class UsersListActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var adapter: UserListRecyclerAdapter
    private val userViewModel by inject<UserViewModel>()
    private lateinit var layoutManager: LinearLayoutManager

//    private var newUserList = mutableListOf<UserEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        layoutManager = LinearLayoutManager(this)
        userViewModel.getUsers().observe(this, Observer {
            adapter = UserListRecyclerAdapter(this, it!!) {
                val detailsIntent = Intent(this, DetailActivity::class.java)
                detailsIntent.putExtra(EXTRA_USER_DETAILS, it.id.toString())
                startActivity(detailsIntent)
            }
            userListView.adapter = adapter
            userListView.layoutManager = layoutManager
            userListView.setHasFixedSize(true)
        })
    }

//    private fun saveToDatabase(newData: List<UserModel>?){
//        if (newData != null) {
//            newData.mapTo(newUserList) {
//                UserEntity(it.id, it.name, it.phone, it.username, it.company.name, it.email, it.address.city, it.website)
//            }
//            userViewModel.setUsers(newUserList)
//        }
//    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwokInfo = connectivityManager.activeNetworkInfo
        return activeNetwokInfo != null && activeNetwokInfo.isConnectedOrConnecting

    }
}
