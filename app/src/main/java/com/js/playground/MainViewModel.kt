package com.js.playground

import android.arch.lifecycle.ViewModel
import com.js.playground.service.TestService
import com.js.playground.utils.MLog
import com.js.playground.utils.SafetyMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val throwable = SafetyMutableLiveData<Throwable>()

    fun testApi() {
        compositeDisposable.add(TestService.instance.test().subscribe(Consumer {
            MLog.e("Success")
        }, throwable))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}