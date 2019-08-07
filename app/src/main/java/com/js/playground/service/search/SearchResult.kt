package com.js.playground.service.search

import com.google.gson.annotations.SerializedName

data class SearchResults(@SerializedName("organic_results") val results: List<SearchResult>,
                         @SerializedName("serpapi_pagination") val pagination: SearchPagination)

data class SearchResult(val position: Int,
                        val link: String,
                        val title: String)

data class SearchPagination(val next_link: String)