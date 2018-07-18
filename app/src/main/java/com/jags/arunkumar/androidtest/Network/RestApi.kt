package com.jags.arunkumar.androidtest.Network

import com.jags.arunkumar.androidtest.data.DeliveryData
import retrofit2.http.GET

interface RestApi {
    @GET("/deliveries")
    fun requestDeliveryResult(): io.reactivex.Observable<List<DeliveryData>>
}