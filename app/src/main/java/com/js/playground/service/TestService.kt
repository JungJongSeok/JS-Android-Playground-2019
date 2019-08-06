package com.js.playground.service

import io.reactivex.Single

class TestService {
    private object Holder {
        val INSTANCE = TestService()
    }

    companion object {
        @JvmStatic
        val instance by lazy {
            Holder.INSTANCE
        }
    }

    fun test(): Single<Any> {
        return ApiProvider.of(TestApi::class)
                .list()
    }
}