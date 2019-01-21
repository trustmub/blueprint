package com.example.trmubaiwa.blueprint.Services

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.trmubaiwa.blueprint.Models.TimerState
import java.util.*

class TimerTaskService : TimerTask() {

    var timerState: MutableLiveData<TimerState> = MutableLiveData()

    override fun run() {
        timerState.postValue(TimerState(false))
        Log.d("timer", "Timer Service invoked")
    }
}