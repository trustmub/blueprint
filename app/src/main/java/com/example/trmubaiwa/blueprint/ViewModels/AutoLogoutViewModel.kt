package com.example.trmubaiwa.blueprint.ViewModels

import android.arch.lifecycle.ViewModel
import com.example.trmubaiwa.blueprint.Repositories.ALRepository


class AutoLogoutViewModel constructor(private val repository: ALRepository) : ViewModel() {

    fun getActiveExpirationTime(): String = repository.getActiveExpirationTime()

}