package com.js.playground

import android.arch.lifecycle.ViewModel
import com.js.playground.service.TestService
import com.js.playground.service.search.SearchResults
import com.js.playground.utils.SafetyMutableLiveData
import io.reactivex.disposables.CompositeDisposable

class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val testResult = SafetyMutableLiveData<SearchResults>()
    val throwable = SafetyMutableLiveData<Throwable>()

    fun testApi() {
        compositeDisposable.add(TestService.instance.test().subscribe(testResult, throwable))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}