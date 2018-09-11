package com.example.trmubaiwa.blueprint.activities.autologout

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.ViewModels.AutoLogoutViewModel
import kotlinx.android.synthetic.main.fragment_count_down.*
import kotlinx.android.synthetic.main.fragment_count_down.view.*
import org.koin.android.ext.android.inject
import java.util.*


class CountDownDialogFragment : DialogFragment() {

    private val autoLogoutViewModel by inject<AutoLogoutViewModel>()

    private var timer: Timer? = null
    private lateinit var timerTask: TimerTask
    private var seconds = autoLogoutViewModel.getActiveExpirationTime().toLong()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater!!.inflate(R.layout.fragment_count_down, container, false)

        /** prevent dialog dismissal by pressing outside the dialog or back button*/
        this.dialog.let {
            it.setCanceledOnTouchOutside(false)
            it.setOnKeyListener { _, keyCode, _ -> keyCode == KeyEvent.KEYCODE_BACK }
        }
        countDownTimer()
        view.message_text_count_down.text = getString(R.string.logout_countdown_message, seconds)
        view.btn_logout_count_down.setOnClickListener {
            stopCountDownTimer()
            dismiss()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.setFinishOnTouchOutside(false)

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getLong("sec")
        }
    }

    private fun countDownTimer() {
        timer?.cancel()

        timer = Timer()

        timerTask = object : TimerTask() {
            override fun run() {
                seconds--
                if (activity != null) activity.runOnUiThread { message_text_count_down?.text = getString(R.string.logout_countdown_message, seconds) }
                if (seconds == 0L) logoutApplication()
            }
        }
        /** update the UI with new value every second */
        timer?.scheduleAtFixedRate(timerTask, 1000, 1000)

    }

    fun stopCountDownTimer() {
        timerTask.cancel()
    }

    private fun logoutApplication() {
        dismiss()
    }

}
