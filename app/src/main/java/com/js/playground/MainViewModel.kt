package com.js.playground

import android.arch.lifecycle.ViewModel
import com.js.playground.service.SearchService
import com.js.playground.service.search.SearchResults
import com.js.playground.utils.ErrorParser
import com.js.playground.utils.SafetyMutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit


class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val testResult = SafetyMutableLiveData<SearchResults>()
    val throwable = SafetyMutableLiveData<Throwable>()

    private val lock = PublishSubject.create<Boolean>()

    fun searchApi(q: String?) {
        lock.onNext(true)
        if (q.isNullOrEmpty()) {
            return
        }
        compositeDisposable.add(SearchService.instance.search(q)
                .delaySubscription(1000, TimeUnit.MILLISECONDS)
                .takeUntil(lock.firstElement().toFlowable())
                .subscribe(testResult, Consumer {
                    if (it is CancellationException) {
                        return@Consumer
                    }
                    if (it is HttpException) {
                        throwable.setValueSafety(IllegalStateException(ErrorParser.parse(it).error))
                    } else {
                        throwable.setValueSafety(it)
                    }
                }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}