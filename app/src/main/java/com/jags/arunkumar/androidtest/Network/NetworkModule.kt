package com.jags.arunkumar.androidtest.Network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jags.arunkumar.androidtest.AndroidTestApplication
import com.jags.arunkumar.androidtest.BuildConfig
import com.jags.arunkumar.androidtest.Network.NetworkUtil.CacheConfig
import com.jags.arunkumar.androidtest.Network.NetworkUtil.NetworkUtil
import dagger.Module
import dagger.Provides
import okhttp3.Cache
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
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = { chain: Interceptor.Chain? ->

            val originalResponse = chain?.proceed(chain.request())
            if (NetworkUtil.hasNetwork()) {
                originalResponse?.newBuilder()
                    ?.header("Cache-Control", "public, max-age=${CacheConfig.timeout}")
                    ?.build()
            } else {
                originalResponse?.newBuilder()
                    ?.header("Cache-Control", "public, only-if-cached, max-stale=${CacheConfig.cacheTime}")
                    ?.build()
            }

        }
        val client = OkHttpClient.Builder()
            .cache(Cache(File(AndroidTestApplication.sharedInstance.cacheDir, CacheConfig.cacheFileName), CacheConfig.httpCacheSize))
            .addInterceptor(interceptor)
            .readTimeout(CacheConfig.timeout, TimeUnit.SECONDS)
            .connectTimeout(CacheConfig.timeout, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            /** network log interceptor*/
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            client.addInterceptor(logging)
        }
        return client.build()
    }

    @Provides

    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://30.100.25.79:8080")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideContentService(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }
}