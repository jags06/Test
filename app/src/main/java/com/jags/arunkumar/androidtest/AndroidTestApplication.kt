package com.jags.arunkumar.androidtest

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.support.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.jags.arunkumar.androidtest.DI.DaggerAppComponent
import com.jags.arunkumar.androidtest.Network.NetworkUtil.NetworkUtil
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class AndroidTestApplication : Application(), HasActivityInjector {
    companion object {
        lateinit var sharedInstance: AndroidTestApplication
    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
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
        (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.registerNetworkCallback(NetworkRequest.Builder().build(), object : ConnectivityManager.NetworkCallback() {
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