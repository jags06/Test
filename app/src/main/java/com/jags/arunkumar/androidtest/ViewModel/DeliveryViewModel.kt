package com.jags.arunkumar.androidtest.ViewModel

import com.jags.arunkumar.androidtest.Network.RestApi
import com.jags.arunkumar.androidtest.data.DeliveryData
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

interface DeliveryViewModelInteractor {
    val provideList: BehaviorRelay<List<DeliveryData>>
    val provideError: BehaviorRelay<String>
}

class DeliveryViewModel(private var service: RestApi) : DeliveryViewModelInteractor {
    override val provideError: BehaviorRelay<String> = BehaviorRelay.create()
    override val provideList: BehaviorRelay<List<DeliveryData>> = BehaviorRelay.create()


    fun getRequestData() {
        service.requestDeliveryResult()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                when (it) {
                    is IOException -> provideError.accept("Make sure server is up and running.")
                    is HttpException ->
                        when (it.code()) {
                            500 -> provideError.accept("Internal server Error !")
                            404 -> provideError.accept("Bad request ")
                        }
                }
                return@onErrorReturn emptyList<DeliveryData>()

            }

            .subscribe { provideList.accept(it) }


    }


}
