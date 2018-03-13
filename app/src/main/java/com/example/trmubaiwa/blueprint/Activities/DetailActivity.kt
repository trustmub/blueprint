package com.example.trmubaiwa.blueprint.Activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Utilities.EXTRA_USER_DETAILS
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity() {
    private val userViewModel by inject<UserViewModel>()
    private var phoneNumber = ""
    private var email = ""
    private var location = arrayOf<Float>()
private var detailsState = DetailsState.CLOSED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val passedUserDetails = intent.getStringExtra(EXTRA_USER_DETAILS)
        Log.d("User", "The Passed Value is $passedUserDetails ")

        if (passedUserDetails != null) {
            userViewModel.getUsers().observe(this, Observer {
                it?.filter {
                    it.id == passedUserDetails.toInt()
                }!!.map {
                    Log.d("User", "The Name is ${it.name} ")
                    phoneNumber = it.phone
                    email = it.email
                    location = arrayOf(it.address.geo.lat.toFloat(), it.address.geo.lng.toFloat())
                }
            })
        }

        fab.setOnClickListener { view ->
            fab.toggle()
//            if(detailsState == DetailsState.CLOSED){
//                fab.layoutParams.height = 500
//                detailsState = DetailsState.OPENED
//            } else{
//                fab.layoutParams.height = 16
//                detailsState = DetailsState.CLOSED
//            }

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    enum class DetailsState{OPENED, CLOSED}
}
