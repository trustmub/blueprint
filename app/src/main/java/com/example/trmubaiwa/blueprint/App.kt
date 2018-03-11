package com.example.trmubaiwa.blueprint

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
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


open class App : Application() {

    override fun onCreate() {
        super.onCreate()

        /** Start Koin dependency injection */
        startKoin(this, listOf(getViewModelModules(), getRepositoryModule(), getLibrariesModule(), getDatabaseModule(), getApiModule()))
    }

    /** for all your view models that you will need to inject */
    open fun getViewModelModules(): Module = applicationContext {
        viewModel { UserViewModel(get()) }
    }

    /** for all you repositories that you need to inject */
    open fun getRepositoryModule(): Module = applicationContext {
        provide { UserRepository(get(), get(),get()) }
    }

    /**  */
    open fun getLibrariesModule(): Module = applicationContext {
        provide(name = "context") { applicationContext }
        provide(isSingleton = true) { createAppDatabase(get()) }
        provide(isSingleton = false) { createSomeDefinedDao() }
        provide(isSingleton = true) { createErrorConverter(get()) }
    }

    open fun getDatabaseModule(): Module = applicationContext {
        provide(isSingleton = true) { createAppDatabase(get("context")) }
        provide(isSingleton = true) { createAppDatabase(get("context")).userDao() }
    }

    /**
     * Set You APIs here
     * */
    open fun getApiModule(): Module = org.koin.dsl.module.applicationContext {
        provide(isSingleton = true) { get<Retrofit>().create<Webservice>(Webservice::class.java) }
        provide(isSingleton = true) {
            Retrofit.Builder()
                    .baseUrl("http://jsonplaceholder.typicode.com")
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
        }


    }

    /** We are using the Room Objed Relational mapper for android
     *  Which takes context as an argument
     *  Note: change the name of the database relevant to your app
     */
    private fun createAppDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "blueprint_db").build()

    /** if you have a DAO you create a function for it from the template below
     *
     */
    private fun createSomeDefinedDao() {}

    /**
     *
     */
    private fun createErrorConverter(retrofit: Retrofit): Converter<ResponseBody, Error> =
            retrofit.responseBodyConverter(Error::class.java, arrayOfNulls<Annotation>(0))

}