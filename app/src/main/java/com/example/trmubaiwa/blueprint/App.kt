package com.example.trmubaiwa.blueprint

import android.app.Application
import android.content.Context
import com.example.trmubaiwa.blueprint.Repositories.UserRepository
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        /** Start Koin dependency injection */
        startKoin(this, listOf(getViewModelModules(), getRepositoryModule(), getLibrariesModule()))
    }

    /** for all your view models that you will need to inject */
    open fun getViewModelModules(): Module = applicationContext {
        viewModel { UserViewModel() }
    }

    /** for all you repositories that you need to inject */
    open fun getRepositoryModule(): Module = applicationContext {
        provide { UserRepository() }
    }

    /**  */
    open fun getLibrariesModule(): Module = applicationContext {
        provide(name = "context") { applicationContext }
        provide(isSingleton = true) { createRetrofit() }
        provide(isSingleton = true) { createAppDatabase(get()) }
        provide(isSingleton = false) { createSomeDefinedDao() }
        provide(isSingleton = true) { createErrorConverter(get()) }
    }


    private fun createRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl("some urlhere")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    /** We are using the Room Objed Relational mapper for android
     *  Which takes context as an argument
     */
    private fun createAppDatabase(context: Context) {}

    /** if you hava a DAO you create a function for it from the template below
     *
     */
    private fun createSomeDefinedDao() {}

    /**
     *
     */
    private fun createErrorConverter(retrofit: Retrofit): Converter<ResponseBody, Error> =
            retrofit.responseBodyConverter(Error::class.java, arrayOfNulls<Annotation>(0))


}