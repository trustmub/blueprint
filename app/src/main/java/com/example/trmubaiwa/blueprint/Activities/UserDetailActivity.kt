package com.example.trmubaiwa.blueprint.Activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Utilities.EXTRA_USERDETAILS
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel

import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.content_user_detail.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject

class UserDetailActivity : AppCompatActivity() {
    private val userViewModel by inject<UserViewModel>()
    private var phonenumber = ""
    private var email = ""
    var location = arrayOf<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        setSupportActionBar(toolbar)
        val passedUserDetails = intent.getStringExtra(EXTRA_USERDETAILS)
        Log.d("User", "The value is $passedUserDetails ")

        nameDetailsText.text = passedUserDetails
        if (passedUserDetails != null) {
            userViewModel.getUsers().observe(this, Observer {
                it?.filter {
                    it.id == passedUserDetails.toInt()
                }!!.map {
                    nameDetailsText.text = it.name
                    phonenumber = it.phone
                    email = it.email
                    location = arrayOf(it.address.geo.lat.toLong(), it.address.geo.lng.toLong())
                }
            })
        }


        emailImage.setOnClickListener {
            toast("Email clicked")
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }


}
