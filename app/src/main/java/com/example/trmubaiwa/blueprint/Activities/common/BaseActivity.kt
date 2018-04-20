package com.example.trmubaiwa.blueprint.Activities.common

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.trmubaiwa.blueprint.Activities.LoginActivity
import com.example.trmubaiwa.blueprint.App

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /** Interaction method ot detect  */
    override fun onUserInteraction() {
        super.onUserInteraction()
    }


    override fun onSessionLogedOut() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}

