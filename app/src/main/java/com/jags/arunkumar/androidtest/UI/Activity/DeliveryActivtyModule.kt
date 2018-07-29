package com.jags.arunkumar.androidtest.UI.Activity

import com.jags.arunkumar.androidtest.UI.Fragment.DeliveryFragment
import com.jags.arunkumar.androidtest.UI.Fragment.MapFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DeliveryActivtyModule {

    @ContributesAndroidInjector
    abstract fun deliveryFragment(): DeliveryFragment

    @ContributesAndroidInjector
    abstract fun mapFragment(): MapFragment


}