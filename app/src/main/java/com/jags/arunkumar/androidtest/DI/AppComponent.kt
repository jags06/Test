package com.jags.arunkumar.androidtest.DI

import android.app.Application
import com.jags.arunkumar.androidtest.AndroidTestApplication
import com.jags.arunkumar.androidtest.Network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ActivtiyBindingModule::class, AppModule::class, NetworkModule::class, AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<AndroidTestApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: AndroidTestApplication?)
}