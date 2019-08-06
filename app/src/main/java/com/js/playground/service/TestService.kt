package com.js.playground.service

import com.js.playground.service.search.SearchRequest
import com.js.playground.service.search.SearchResults
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

    fun test(): Single<SearchResults> {
        val searchRequest = SearchRequest("Coffee")
        return ApiProvider.of(TestApi::class)
                .list(searchRequest.q,
                        searchRequest.location,
                        searchRequest.hl,
                        searchRequest.gl,
                        searchRequest.google_domain)
    }
}