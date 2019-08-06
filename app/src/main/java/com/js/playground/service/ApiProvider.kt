package com.js.playground.service

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.js.playground.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class ApiProvider {
    companion object {
        private fun createOkHttpClientBuilder(): OkHttpClient.Builder {
            val apiClientBuilder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                apiClientBuilder.addNetworkInterceptor(StethoInterceptor())
            }
            apiClientBuilder.readTimeout((30 * 1000).toLong(), TimeUnit.MILLISECONDS)
            apiClientBuilder.writeTimeout((30 * 1000).toLong(), TimeUnit.MILLISECONDS)
            apiClientBuilder.connectTimeout((30 * 1000).toLong(), TimeUnit.MILLISECONDS)
            return apiClientBuilder
        }

        fun <T : Any> of(kClass: KClass<T>): T = Retrofit.Builder()
                .client(createOkHttpClientBuilder().build())
                .baseUrl("https://test.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(kClass.java)
    }
}