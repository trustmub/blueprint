package com.example.trmubaiwa.blueprint

import android.app.Application
import android.arch.persistence.room.Room
import com.example.trmubaiwa.blueprint.Repositories.UserRepository
import com.example.trmubaiwa.blueprint.Services.Webservice
import com.example.trmubaiwa.blueprint.Utilities.AppDatabase
import com.example.trmubaiwa.blueprint.ViewModels.UserViewModel
import okhttp3.ResponseBody
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.Executors


open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        /** Start Koin dependency injection */
        startKoin(this, listOf(getGeneralModule(), getDatabaseModule(), getApiModule(), getRepositoryModule(), getViewModelModules(), getLibrariesModule()))
    }


    open fun getGeneralModule() = applicationContext {
        provide(name = "context") { applicationContext }
    }

    /** We are using the Room Object Relational mapper for android
     *  Which takes context as an argument
     *  Note: change the name of the database relevant to your app
     */
    open fun getDatabaseModule(): Module = applicationContext {
        provide(isSingleton = true) { Room.databaseBuilder(get("context"), AppDatabase::class.java, "blueprint-db").build() }
        provide(isSingleton = false) { get<AppDatabase>().userDao() }
    }

    /**
     * Set You APIs here
     * */
    open fun getApiModule(): Module = applicationContext {
        provide(isSingleton = true) { get<Retrofit>().create<Webservice>(Webservice::class.java) }
        provide(isSingleton = true) {
            Retrofit.Builder()
                    .baseUrl("http://jsonplaceholder.typicode.com")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
        }
    }

    /** for all your view models that you will need to inject */
    open fun getViewModelModules(): Module = applicationContext {
        viewModel { UserViewModel(get()) }
    }

    /** for all you repositories that you need to inject */
    open fun getRepositoryModule(): Module = applicationContext {
        provide { UserRepository(get(), get(), get()) }
    }

    /** */
    open fun getLibrariesModule(): Module = applicationContext {
        provide(isSingleton = true) { createErrorConverter(get()) }
        provide(isSingleton = true) { Executors.newCachedThreadPool() }
    }

    /**
     *
     */
    private fun createErrorConverter(retrofit: Retrofit): Converter<ResponseBody, Error> =
            retrofit.responseBodyConverter(Error::class.java, arrayOfNulls<Annotation>(0))


}