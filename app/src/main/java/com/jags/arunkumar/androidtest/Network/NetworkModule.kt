package com.jags.arunkumar.androidtest.Network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jags.arunkumar.androidtest.AndroidTestApplication
import com.jags.arunkumar.androidtest.BuildConfig
import com.jags.arunkumar.androidtest.Network.NetworkUtil.NetworkUtil
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .cache(
                Cache(
                    File(AndroidTestApplication.sharedInstance.cacheDir, "http-cache"),
                    (50 * 1024 * 1024).toLong()
                )
            )
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://192.168.1.17:8080")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideContentService(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOfflineCacheInterceptor(): Interceptor {
        if (!NetworkUtil.hasNetwork()) {
            return Interceptor { chain ->
                var request = chain.request()
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()

                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
                chain?.proceed(request)
            }
        } else {
            return Interceptor { chain ->
                val response = chain.proceed(chain.request())
                val cacheControl = CacheControl.Builder()
                    .maxAge(2, TimeUnit.MINUTES)
                    .build()
                response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
            }
        }

    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BASIC
        } else {
            HttpLoggingInterceptor.Level.NONE

        }
        return logging
    }


}