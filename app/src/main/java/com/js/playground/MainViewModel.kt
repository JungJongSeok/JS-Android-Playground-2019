package com.js.playground

import android.arch.lifecycle.ViewModel
import com.js.playground.service.TestService
import com.js.playground.utils.MLog
import io.reactivex.disposables.CompositeDisposable

class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun testApi() {
        compositeDisposable.add(TestService.instance.test().subscribe({
            MLog.e("Success")
        }, {
            MLog.e("Fail", it)
        }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}