package com.jags.arunkumar.androidtest

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.support.annotation.RequiresApi
import com.jags.arunkumar.androidtest.DI.DaggerAppComponent
import com.jags.arunkumar.androidtest.Network.NetworkUtil.NetworkUtil
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class AndroidTestApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent

    }


    companion object {
        lateinit var sharedInstance: AndroidTestApplication
    }


    override fun onCreate() {
        sharedInstance = this

        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun registerConnectivityNetworkMonitor() {
        (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network?) {
                    super.onAvailable(network)
                    NetworkUtil.setHasNetwork(true)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    NetworkUtil.setHasNetwork(false)
                }

                override fun onLost(network: Network?) {
                    super.onLost(network)
                    NetworkUtil.setHasNetwork(false)
                }
            })
    }


}