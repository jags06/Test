package com.jags.arunkumar.androidtest.Network

import com.jags.arunkumar.androidtest.data.DeliveryData
import io.reactivex.Observable
import retrofit2.http.GET

interface RestApi {
    @GET("/deliveries")
    fun requestDeliveryResult(): Observable<List<DeliveryData>>
}