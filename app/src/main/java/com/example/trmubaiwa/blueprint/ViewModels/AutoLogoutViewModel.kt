package com.example.trmubaiwa.blueprint.ViewModels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.trmubaiwa.blueprint.Enums.ApplicationState
import com.example.trmubaiwa.blueprint.Enums.TaskState
import com.example.trmubaiwa.blueprint.Repositories.ALRepository


class AutoLogoutViewModel constructor(private val repository: ALRepository) : ViewModel() {

    fun getActiveExpirationTime(): String = repository.getActiveExpirationTime()

    fun setApplicationState(appState: ApplicationState) = repository.setApplicationState(appState)

    fun getAutoLogoutTaskState(): LiveData<TaskState> = repository.getAutoLogoutTaskState()

    fun startTimer() = repository.startTimer()

    fun stopTimer() = repository.stopTimer()

}