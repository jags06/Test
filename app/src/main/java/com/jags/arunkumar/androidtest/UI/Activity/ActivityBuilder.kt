package com.jags.arunkumar.androidtest.UI.Activity

import com.jags.arunkumar.androidtest.DI.ActivityScoped
import com.jags.arunkumar.androidtest.UI.Fragment.DeliveryFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [DeliveryActivtyModule::class, DeliveryFragmentModule::class])
    @ActivityScoped
    abstract fun bindMainActivity(): DeliveryActivity


}