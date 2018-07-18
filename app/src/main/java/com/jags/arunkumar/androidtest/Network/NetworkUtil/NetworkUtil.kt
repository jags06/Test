package com.jags.arunkumar.androidtest.Network.NetworkUtil

import android.content.Context
import android.net.ConnectivityManager
import com.jags.arunkumar.androidtest.AndroidTestApplication
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable

object NetworkUtil {
    private val _networkDidChangeStatus: BehaviorRelay<Boolean> = BehaviorRelay.create()
    val networkDidChangeStatus: Observable<Boolean> = _networkDidChangeStatus

    fun hasNetwork(): Boolean {

        val cm = AndroidTestApplication.sharedInstance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun setHasNetwork(hasNetwork: Boolean) {
        _networkDidChangeStatus.accept(hasNetwork)
    }
}