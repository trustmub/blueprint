package com.example.trmubaiwa.blueprint.Utilities.networking

import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.content.Intent
import android.content.BroadcastReceiver


class ConnectionLiveData(val context: Context) : LiveData<ConnectionModel>() {

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, filter)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(networkReceiver)
    }


    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.extras != null) {
                val activeNetwork = intent.extras!!.get(ConnectivityManager.EXTRA_NETWORK_INFO) as NetworkInfo
                val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
                if (isConnected) {
                    when (activeNetwork.type) {
//                        ConnectivityManager.TYPE_WIFI -> postValue(ConnectionModel(ConnectionType.WifiData, true))
                        ConnectivityManager.TYPE_WIFI -> postValue(ConnectionModel(1, true))
                        ConnectivityManager.TYPE_MOBILE -> postValue(ConnectionModel(2, true))
                    }
                } else {
                    postValue(ConnectionModel(0, false))
                }
            }
        }
    }
}