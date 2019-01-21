package com.example.trmubaiwa.blueprint.activities

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.example.trmubaiwa.blueprint.Adapters.OffLineUserListRecyclerAdapter
import com.example.trmubaiwa.blueprint.Adapters.UserListRecyclerAdapter
import com.example.trmubaiwa.blueprint.Models.UserModel
import com.example.trmubaiwa.blueprint.Models.UserParcel
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Rooms.UserEntity
import com.example.trmubaiwa.blueprint.Utilities.EXTRA_USER_DETAILS
import com.example.trmubaiwa.blueprint.Utilities.networking.ConnectionLiveData
import com.example.trmubaiwa.blueprint.Utilities.networking.ConnectionModel
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel
import com.example.trmubaiwa.blueprint.activities.common.BaseActivity
import kotlinx.android.synthetic.main.activity_users_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.koin.android.architecture.ext.viewModel


class UsersListActivity : BaseActivity(), AnkoLogger {

    private lateinit var adapter: UserListRecyclerAdapter
    private lateinit var userDetailsParcelable: Parcelable


    private lateinit var offLineAdapter: OffLineUserListRecyclerAdapter
    private val userViewModel by viewModel<UserViewModel>()
    private lateinit var layoutManager: LinearLayoutManager

    private var newUserList = mutableListOf<UserEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)
        layoutManager = LinearLayoutManager(this)

        val connectionLiveData = ConnectionLiveData(applicationContext)
        connectionLiveData.observe(this, Observer<ConnectionModel> { connection ->
            if (connection!!.isConnected) {
                getListFromApi()
                when (connection.type) {
                    1 -> toast("Wifi turned ON")
                    2 -> toast("Mobile data turned ON")
                }
            } else {
                toast("Connection turned OFF")
                getListFromDatabase()
            }
        })
    }

    private fun displayListOnUi() {
        showProgressBar(true)
        if (isNetworkAvailable()) getListFromApi() else getListFromDatabase()
    }

    private fun getListFromApi() {
        userViewModel.getUsers().observe(this, Observer { it ->
            showProgressBar(false)

            adapter = UserListRecyclerAdapter(this, it!!) {

                Log.d("UserDetails", it.name)
                val detailsIntent = Intent(this, UserDetailActivity::class.java)
                userDetailsParcelable = UserParcel(it.id.toString(), it.name, it.email, it.phone, it.company.name)

                detailsIntent.putExtra(EXTRA_USER_DETAILS, userDetailsParcelable)
                startActivity(detailsIntent)
            }

            saveToDatabase(it)
            userListView.let {
                it.adapter = adapter
                it.layoutManager = layoutManager
                it.setHasFixedSize(true)
            }
        })
    }

    private fun getListFromDatabase() {
        userViewModel.getDbUsers().observe(this, Observer { it ->
            showProgressBar(false)
            offLineAdapter = OffLineUserListRecyclerAdapter(this, it!!) {
                val detailsIntent = Intent(this, UserDetailActivity::class.java)
                userDetailsParcelable = UserParcel(it.id.toString(), it.name, it.email, it.phone, it.company)
                detailsIntent.putExtra(EXTRA_USER_DETAILS, userDetailsParcelable)
//                detailsIntent.putExtra(OFFLINE_EXTRA_USER_DETAILS, it.id.toString())
                startActivity(detailsIntent)
            }
            userListView.let {
                it.adapter = offLineAdapter
                it.layoutManager = layoutManager
                it.setHasFixedSize(true)
            }
        })
    }

    private fun saveToDatabase(newData: List<UserModel>?) {
        if (newData != null) {
            newData.mapTo(newUserList) {
                UserEntity(it.id, it.name, it.phone, it.username, it.company.name, it.email, it.address.city, it.website)
            }
            userViewModel.setUsers(newUserList)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwokInfo = connectivityManager.activeNetworkInfo
        return activeNetwokInfo != null && activeNetwokInfo.isConnectedOrConnecting

    }


    private fun showProgressBar(state: Boolean) {
        progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
