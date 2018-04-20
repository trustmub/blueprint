package com.example.trmubaiwa.blueprint.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.example.trmubaiwa.blueprint.Activities.common.BaseActivity
import com.example.trmubaiwa.blueprint.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFabPrompt()
        floatingActionButton.setOnClickListener {
            startActivity(intentFor<UsersListActivity>())
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
}
