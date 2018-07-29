package com.jags.arunkumar.androidtest.UI.Fragment

import com.jags.arunkumar.androidtest.Network.RestApi
import com.jags.arunkumar.androidtest.ViewModel.DeliveryViewModel
import dagger.Module
import dagger.Provides

@Module
class DeliveryFragmentModule {

    @Provides
    fun provideDeliveryViewModel(restApi: RestApi): DeliveryViewModel {
        return DeliveryViewModel(restApi)
    }

}

