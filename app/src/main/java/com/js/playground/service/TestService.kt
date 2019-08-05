package com.js.playground.service

import android.util.Log
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import io.reactivex.schedulers.Schedulers

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

    fun test() {
        val dispose = ApiProvider.retrofitApi(TestApi::class)
                .list()
                .subscribeOn(Schedulers.io())
                .subscribe { t1, t2 ->
                    Log.e("aaaaaa", "test", t2)
                }
    }
}