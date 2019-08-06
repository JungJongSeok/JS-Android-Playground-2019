package com.js.playground.utils

import com.google.gson.Gson
import com.js.playground.service.ErrorResult
import retrofit2.HttpException

class ErrorParser {
    companion object {
        fun parse(throwable: HttpException): ErrorResult {
            val errorBody = throwable.response().errorBody()?.string() ?: ""
            return Gson().fromJson(errorBody, ErrorResult::class.java)
        }
    }
}