package com.jags.arunkumar.androidtest.UI

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.jags.arunkumar.androidtest.Network.RestApi
import com.jags.arunkumar.androidtest.R
import com.jags.arunkumar.androidtest.UI.Adapter.DeliveryListAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class DeliveryActivity : AppCompatActivity(), HasFragmentInjector {
    @Inject
    lateinit var service: RestApi
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun fragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delivery_data)
        AndroidInjection.inject(this)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val requestDeliveryResult = service.requestDeliveryResult()
            .subscribeOn(Schedulers.io())

            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Timber.d("Check the Object${it[0].locationData.lat}")

            }

            .doOnError {
                if (it is HttpException) {
                    val response = it
                    val code = response.code()
                    Timber.d("Check the code $code")
                } else {

                    Timber.d("Check throwable Exception ${it.message}")
                }
            }
        requestDeliveryResult.subscribe {
            recyclerView.adapter = DeliveryListAdapter(it)
        }
    }
}