package com.js.playground.utils

interface Settable<in T> {
    fun setData(t: T)
}