package com.js.playground.service

import com.js.playground.service.search.SearchResults
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search.json")
    fun list(@Query("q") q: String,
             @Query("location") location: String,
             @Query("hl") hl: String,
             @Query("gl") gl: String,
             @Query("google_domain") google_domain: String): Single<SearchResults>

    @GET("search.json")
    fun listMore(@Query("q") q: String,
                 @Query("location") location: String,
                 @Query("hl") hl: String,
                 @Query("gl") gl: String,
                 @Query("google_domain") google_domain: String,
                 @Query("start") start: String): Single<SearchResults>
}