package com.example.trmubaiwa.blueprint

import android.app.Application
import com.example.trmubaiwa.blueprint.Repositories.UserRepository
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.applicationContext


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(getViewModelModules(), getRepositoryModule()))
    }

    /** for all your view models that you will need to inject */
    fun getViewModelModules() = applicationContext {
        viewModel { UserViewModel() }
    }

    /** for all you repositories that you need to inject */
    fun getRepositoryModule() = applicationContext {
        provide { UserRepository() }
    }



}