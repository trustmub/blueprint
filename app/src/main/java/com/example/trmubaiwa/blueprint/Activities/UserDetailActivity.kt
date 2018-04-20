package com.example.trmubaiwa.blueprint.Activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.trmubaiwa.blueprint.Activities.common.BaseActivity
import com.example.trmubaiwa.blueprint.Enums.DataAccessType
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Utilities.EXTRA_USER_DETAILS
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel

import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.content_user_detail.*
import org.jetbrains.anko.email
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class UserDetailActivity : BaseActivity() {
    private val userViewModel by inject<UserViewModel>()
    private var phonenumber = ""
    private var email = ""
    private var location = arrayOf<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        setSupportActionBar(toolbar)

        val passedUserDetails = intent.getStringExtra(EXTRA_USER_DETAILS)

        Log.d("User", "The value is $passedUserDetails ")
        Log.d("User", "The value is ${DataAccessType.values().get(1)} ")

        nameDetailsText.text = passedUserDetails

        if (passedUserDetails != null) {
            Log.d("User", "The value at index 0 Zero ${passedUserDetails[0]} ")
            userViewModel.getUsers().observe(this, Observer {
                it?.filter {
                    it.id == passedUserDetails.toInt()
                }!!.map {
                    nameDetailsText.text = it.name
                    Log.d("User", "The value is ${nameDetailsText.text} ")

                    phonenumber = it.phone
                    email = it.email
                    location = arrayOf(it.address.geo.lat.toFloat(), it.address.geo.lng.toFloat())
                }
            })
        }


        emailImage.setOnClickListener {
            toast("Email clicked $email ")
            email(email, "Send from application", "Email body content")
        }
        callImage.setOnClickListener {
            toast("Call Icon Clicked $phonenumber")

        }
        locationImage.setOnClickListener {
            toast("Location clicked with ${location[0]} and ${location[1]}")
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }


}
