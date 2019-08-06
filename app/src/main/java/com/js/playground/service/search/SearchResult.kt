package com.js.playground.service.search

import com.google.gson.annotations.SerializedName

data class SearchResults(@SerializedName("local_results") val results: List<SearchResult>)

data class SearchResult(val position: Int,
                        val place_id: String,
                        val place_id_search: String,
                        val title: String,
                        val rating: Float,
                        val reviews: Int,
                        val type: String,
                        val hours: String,
                        val address: String,
                        val thumbnail: String)

