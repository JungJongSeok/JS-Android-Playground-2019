package com.js.playground

import androidx.lifecycle.ViewModel
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.js.playground.extension.BackgroundThreadExecutor
import com.js.playground.extension.UiThreadExecutor
import com.js.playground.service.SearchService
import com.js.playground.service.search.SearchResults
import com.js.playground.utils.ErrorParser
import com.js.playground.utils.MLog
import com.js.playground.utils.SafetyMutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicReference


class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val testResult = SafetyMutableLiveData<SearchResults>()
    val submitList = SafetyMutableLiveData<PagedList<TypeSearchResult>>()
    val throwable = SafetyMutableLiveData<Throwable>()

    private val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(20) // 첫번째 로드가 될때 그릴 아이템 size
            .setPageSize(8) // pagination 크기
            .setPrefetchDistance(5) // 미리 그려 놓을 아이템 size
            .setEnablePlaceholders(true) // null 아이템을 미리 그려 놓을지의 flag
            .build()
    private val dataSource = object : PageKeyedDataSource<String, TypeSearchResult>() {
        override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, TypeSearchResult>) {
            searchApi(callback)
        }

        override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, TypeSearchResult>) {
            searchMoreApi(params.key, callback)
        }

        override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, TypeSearchResult>) {
        }
    }

    private val searchText = AtomicReference("")
    private val lock = PublishSubject.create<Boolean>()

    fun search(q: String?) {
        searchText.set(q)
        lock.onNext(true)
    }

    fun searchApi(callback: PageKeyedDataSource.LoadInitialCallback<String, TypeSearchResult>) {
        if (searchText.get().isNullOrEmpty()) {
            return
        }
        compositeDisposable.add(SearchService.instance.search(searchText.get())
                .delaySubscription(1000, TimeUnit.MILLISECONDS)
                .takeUntil(lock.firstElement().toFlowable())
                .subscribe(Consumer {
                    callback.onResult(it.results.map { searchResult ->
                        TypeSearchResult(Type.SEARCH_TYPE, searchResult)
                    }, "", it.pagination.next_link)
                }, Consumer {
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

    fun searchMoreApi(key: String, callback: PageKeyedDataSource.LoadCallback<String, TypeSearchResult>) {
        compositeDisposable.add(SearchService.instance.searchMore(searchText.get(), key)
                .subscribe(Consumer {
                    callback.onResult(it.results.map { searchResult ->
                        TypeSearchResult(Type.SEARCH_TYPE, searchResult)
                    }, it.pagination.next_link)
                }, Consumer {
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

    fun setPagedList() {
        compositeDisposable.add(Single.fromCallable {
            return@fromCallable PagedList.Builder(dataSource, config)
                    .setNotifyExecutor(UiThreadExecutor)
                    .setFetchExecutor(BackgroundThreadExecutor)
                    .setBoundaryCallback(object : PagedList.BoundaryCallback<TypeSearchResult>() {
                        override fun onZeroItemsLoaded() {
                            super.onZeroItemsLoaded()
                            MLog.e("onZeroItemsLoaded")
                        }
                    })
                    .build()
        }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(submitList, throwable))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}