package com.js.playground

import android.arch.lifecycle.ViewModel
import com.js.playground.service.SearchService
import com.js.playground.service.search.SearchResults
import com.js.playground.utils.SafetyMutableLiveData
import io.reactivex.disposables.CompositeDisposable

class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val testResult = SafetyMutableLiveData<SearchResults>()
    val throwable = SafetyMutableLiveData<Throwable>()

    fun searchApi(q: String) {
        compositeDisposable.add(SearchService.instance.search(q).subscribe(testResult, throwable))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}