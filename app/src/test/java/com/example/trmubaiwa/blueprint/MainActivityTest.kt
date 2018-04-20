package com.example.trmubaiwa.blueprint

import android.content.Context
import android.preference.PreferenceManager
import com.example.trmubaiwa.blueprint.Activities.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class MainActivityTest() {

    @Before
    fun setup() {
    }

    @Test
    fun checkIfTestAreRunning() {
        val activity = Robolectric.buildActivity(MainActivity::class.java)
        val preferenceManager = PreferenceManager.getDefaultSharedPreferences(activity.get().applicationContext)
        assert(preferenceManager.getBoolean("didShowPrompt", false))
    }
}
