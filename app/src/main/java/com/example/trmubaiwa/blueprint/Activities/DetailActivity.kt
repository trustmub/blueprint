package com.example.trmubaiwa.blueprint.Activities

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.example.trmubaiwa.blueprint.Activities.common.BaseActivity
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Utilities.EXTRA_USER_DETAILS
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.sample_fragment_layout.*
import org.koin.android.ext.android.inject


class DetailActivity : BaseActivity(), View.OnClickListener {


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

            Log.d("User", "The value at index 0 Zero ${passedUserDetails[0]} ")

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

        expandable_layout_0.setOnExpansionUpdateListener({ _, state -> Log.d("ExpandableLayout0", "State: $state") })

        expandable_layout_1.setOnExpansionUpdateListener({ _, state -> Log.d("ExpandableLayout1", "State: $state") })
        expandable_layout_1.collapse()
        expandable_layout_0.collapse()

        expand_button.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when {
            expandable_layout_0.isExpanded -> expandable_layout_0.collapse()
            expandable_layout_1.isExpanded -> expandable_layout_1.collapse()
            else -> {
                expandable_layout_0.expand()
                expandable_layout_1.expand()
            }
        }
    }

    enum class DetailsState { OPENED, CLOSED }
}
