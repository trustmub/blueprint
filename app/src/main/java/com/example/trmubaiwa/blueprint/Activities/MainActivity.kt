package com.example.trmubaiwa.blueprint.Activities

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.DialogFragment
import android.util.Log
import com.example.trmubaiwa.blueprint.Activities.autologout.CountDownDialogFragment
import com.example.trmubaiwa.blueprint.Activities.common.BaseActivity
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.ViewModels.AutoLogoutViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import org.koin.android.ext.android.inject
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt

class MainActivity : BaseActivity() {

    private val aLViewModel by inject<AutoLogoutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        aLViewModel.getActiveExpirationTime()

        showFabPrompt()

        floatingActionButton.setOnClickListener {
            startActivity(intentFor<UsersListActivity>())
        }

        btn_start_countdown.setOnClickListener {
            Log.d("Timer","start Countdown clicked")
            showDialof(CountDownDialogFragment())

        }

    }

    private fun showFabPrompt() {
        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)
        if (!prefManager.getBoolean("didShowPrompt", false)) {
            MaterialTapTargetPrompt.Builder(this)
                    .setTarget(floatingActionButton)
                    .setPrimaryText("Click here for client list")
                    .setSecondaryText("The list is available offline after first successful view")
                    .setBackButtonDismissEnabled(true)
                    .setPromptStateChangeListener { prompt, state ->
                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED || state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {
                            val prefEditor = prefManager.edit()
                            prefEditor.putBoolean("didShowPrompt", true)
                            prefEditor.apply()
                        }
                    }
                    .show()

        }
    }

    @SuppressLint("CommitTransaction")
    private fun showDialof(fragment: DialogFragment){
        val transaction = supportFragmentManager.beginTransaction()

        val previouseDialog = supportFragmentManager.findFragmentByTag("dialog")
        if (previouseDialog != null) transaction.remove(previouseDialog)
        fragment.show(transaction, "dialog")
    }
}
