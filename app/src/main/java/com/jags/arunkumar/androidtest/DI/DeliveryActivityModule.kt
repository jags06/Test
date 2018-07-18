package com.jags.arunkumar.androidtest.DI

import android.app.Activity
import com.jags.arunkumar.androidtest.UI.DeliveryActivity
import dagger.Binds
import dagger.Module

@Module
abstract class DeliveryActivityModule {
    @Binds
    abstract fun provideActivity(activity: DeliveryActivity): Activity
}