package com.js.playground.service

import io.reactivex.Single
import retrofit2.http.POST

interface TestApi {
    @POST("list")
    fun list(): Single<Any>
}