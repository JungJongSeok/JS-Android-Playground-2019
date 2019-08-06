package com.js.playground.service

import com.js.playground.service.search.SearchRequest
import com.js.playground.service.search.SearchResults
import io.reactivex.Single

class SearchService {
    private object Holder {
        val INSTANCE = SearchService()
    }

    companion object {
        @JvmStatic
        val instance by lazy {
            Holder.INSTANCE
        }
    }

    fun search(q: String): Single<SearchResults> {
        val searchRequest = SearchRequest(q)
        return ApiProvider.of(SearchApi::class)
                .list(searchRequest.q,
                        searchRequest.location,
                        searchRequest.hl,
                        searchRequest.gl,
                        searchRequest.google_domain)
    }
}