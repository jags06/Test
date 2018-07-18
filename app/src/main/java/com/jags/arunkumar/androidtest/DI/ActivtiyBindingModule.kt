package com.jags.arunkumar.androidtest.DI

import com.jags.arunkumar.androidtest.UI.DeliveryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivtiyBindingModule {
    @ContributesAndroidInjector(modules = [(DeliveryActivityModule::class)])
    abstract fun deliveryActivity(): DeliveryActivity
}