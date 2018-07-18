package com.jags.arunkumar.androidtest.UI

import android.content.Context
import dagger.android.AndroidInjection
import dagger.android.DaggerFragment

class DeliveryFragment: DaggerFragment() {
    override fun onAttach(context: Context?) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }
}