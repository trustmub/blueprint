package com.example.trmubaiwa.blueprint.activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.trmubaiwa.blueprint.activities.common.BaseActivity
import com.example.trmubaiwa.blueprint.Enums.DataAccessType
import com.example.trmubaiwa.blueprint.Models.UserParcel
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Utilities.EXTRA_USER_DETAILS
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel

import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.content_user_detail.*
import org.koin.android.ext.android.inject

class UserDetailActivity : BaseActivity() {
    private val userViewModel by inject<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        setSupportActionBar(toolbar)

        val passedUserDetails = intent.getParcelableExtra<UserParcel>(EXTRA_USER_DETAILS)

        cv_address_card.visibility = View.GONE

        if (passedUserDetails != null) {
            Log.d("User", "The value at index 0 Zero $passedUserDetails ")
            tv_profile_name.text = passedUserDetails.name
            tv_email.text = passedUserDetails.email
            tv_initials.text = getInitials(passedUserDetails.name)

            userViewModel.getUsers().observe(this, Observer { userList ->

                userList?.filter {
                    it.id == passedUserDetails.id.toInt()
                }!!.map {
                    cv_address_card.visibility = View.VISIBLE

                    tv_profile_initials.text = getInitials(it.name)
                    tv_address_city.text = it.address.city
                    tv_address_street.text = it.address.street
                    tv_address_zip.text = it.address.zipcode
                    tv_address_title.text = getString(R.string.address_title, it.name)
                }
            })
        }
    }

    fun closeAddress(v: View) {
        cv_address_card.visibility = View.GONE
    }

    private fun getInitials(name: String): String {
        var initials = ""
        for (i in name.split(" ")) initials += i[0]
        return initials.toUpperCase()
    }


}
