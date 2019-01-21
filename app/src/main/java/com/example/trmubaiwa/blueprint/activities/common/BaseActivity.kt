package com.example.trmubaiwa.blueprint.activities.common

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.trmubaiwa.blueprint.Enums.ApplicationState
import com.example.trmubaiwa.blueprint.Enums.TaskState
import com.example.trmubaiwa.blueprint.ViewModels.AutoLogoutViewModel
import com.example.trmubaiwa.blueprint.activities.autologout.CountDownDialogFragment
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity() {

    private val autoLogoutViewModel by inject<AutoLogoutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        autoLogoutViewModel.setApplicationState(ApplicationState.FOREGROUNDED)
        autoLogoutViewModel.startTimer()
        autoLogoutViewModel.getAutoLogoutTaskState().observe(this, Observer<TaskState> {
            when (it) {
                TaskState.START -> Log.d("timer", "Start observer invoked")
                TaskState.EXPIRED -> Log.d("timer", "Expired observer invoked")
                TaskState.COUNT_DOWN -> showDialog(CountDownDialogFragment())
                TaskState.STOP -> autoLogoutViewModel.stopTimer()
                else -> Log.d("timer", "Something else invoked : $it")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        autoLogoutViewModel.setApplicationState(ApplicationState.FOREGROUNDED)
        autoLogoutViewModel.startTimer()
    }

    override fun onPause() {
        super.onPause()
        autoLogoutViewModel.setApplicationState(ApplicationState.BACKGROUNDED)
        autoLogoutViewModel.startTimer()
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        autoLogoutViewModel.startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        autoLogoutViewModel.setApplicationState(ApplicationState.BACKGROUNDED)
        autoLogoutViewModel.stopTimer()


    }

    @SuppressLint("CommitTransaction")
    private fun showDialog(fragment: DialogFragment) {
        Log.d("timer", "Dialog started")
        val transaction = supportFragmentManager.beginTransaction()

        val previouseDialog = supportFragmentManager.findFragmentByTag("dialog")
        if (previouseDialog != null) transaction.remove(previouseDialog)
        fragment.show(transaction, "dialog")
    }

}

