package com.js.playground.service

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.js.playground.BuildConfig
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

        fun <T: Any> retrofitApi(kClass: KClass<T>): T = Retrofit.Builder()
                .client(createOkHttpClientBuilder().build())
                .baseUrl("https://test.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(kClass.java)
    }
}