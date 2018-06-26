package com.example.trmubaiwa.blueprint.Repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.trmubaiwa.blueprint.activities.common.BaseActivity
import com.example.trmubaiwa.blueprint.BuildConfig
import com.example.trmubaiwa.blueprint.Enums.ApplicationState
import com.example.trmubaiwa.blueprint.Enums.TaskState
import com.example.trmubaiwa.blueprint.R
import com.example.trmubaiwa.blueprint.Utilities.CACHE_EXPIRATION_VALUE
import com.example.trmubaiwa.blueprint.Utilities.RESERVE_FOR_TIMER_VALUE
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import java.util.*

/**
 * Created by trustmub on 2018/04/24.
 */
class ALRepository constructor(private val remoteConfig: FirebaseRemoteConfig){
    private var timer: Timer? = null
    private var applicationViewState: ApplicationState
    private var autoLogoutStatus: MutableLiveData<TaskState> = MutableLiveData()

    private val remoteConfigSettings = FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG).build()

    init {
        autoLogoutStatus.postValue(TaskState.STOP)
        applicationViewState = ApplicationState.FOREGROUNDED

        remoteConfig.setConfigSettings(remoteConfigSettings)
        remoteConfig.setDefaults(R.xml.remote_config)
        getRemoteValues()
    }

    fun startTimer() {
        Log.d("Timer", "Default timer: ${remoteConfig.getString("auto_logout_timeout")}")
        var waitingTime = convertStringToLong()
        stopTimer()
        timer = Timer()
        waitingTime = when (applicationViewState) {
            ApplicationState.FOREGROUNDED -> (waitingTime - RESERVE_FOR_TIMER_VALUE)
            ApplicationState.BACKGROUNDED -> waitingTime
        }

        Log.d("timer", "onStartTimer ApplicationState : ${applicationViewState}")
        timer?.schedule(TimerTaskRunner(), waitingTime)
    }

    inner class TimerTaskRunner : TimerTask() {
        override fun run() {
            when (applicationViewState) {
                ApplicationState.FOREGROUNDED -> autoLogoutStatus.postValue(TaskState.COUNT_DOWN)
                ApplicationState.BACKGROUNDED -> autoLogoutStatus.postValue(TaskState.EXPIRED)
            }

            stopTimer()
        }
    }

    fun stopTimer() {
        if (timer != null) timer?.cancel()
    }

    fun getAutoLogoutTaskState(): LiveData<TaskState> = autoLogoutStatus

    fun setAutoLogoutTaskStatus(taskStatus: TaskState) = autoLogoutStatus.postValue(taskStatus)

    fun getApplicationState(): ApplicationState = applicationViewState

    fun setApplicationState(appState: ApplicationState) {
        applicationViewState = appState
    }

    fun getActiveExpirationTime(): String {
        Log.d("timer", "Timer is ${remoteConfig.getString("auto_logout_timeout")}")

        return remoteConfig.getString("auto_logout_timeout")
    }

    fun resetFlagsOnLogIn() {
        Log.d("Timer", "TaskState and Application State Reset ------------------ ")
        autoLogoutStatus.postValue(TaskState.STOP)
        applicationViewState = ApplicationState.FOREGROUNDED
    }

    /**
     * Converts the string value from firebase or from XML string resource to Long as waitingTimer
     * Value in the startTimer method
     * @Return Type: Long
     */
    private fun convertStringToLong(): Long {
        val convertedTimerValue = remoteConfig.getString("auto_logout_timeout").toDouble() * 60 * 1000
        return convertedTimerValue.toLong()
    }

    //
    private fun getRemoteValues() {
        var cacheExpiration: Long = CACHE_EXPIRATION_VALUE

        //expire the cache immediately for development mode.
        if (remoteConfig.info.configSettings.isDeveloperModeEnabled) {
            cacheExpiration = 5
        }

        remoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(BaseActivity(), { task ->
                    if (task.isSuccessful) {
                        // task successful. Activate the fetched data
                        remoteConfig.activateFetched()
                    } else {
                        //task failed
                        Log.e("Timer", "Fetch Failed: ${task.exception}")
                    }
                })
    }
}